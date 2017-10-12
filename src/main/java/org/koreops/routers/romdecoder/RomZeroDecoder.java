/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.koreops.routers.romdecoder;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.koreops.routers.romdecoder.utils.BitReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author etmatix <a href="https://github.com/etmatrix/">Etmatrix</a>
 * @author Sudipto Sarkar (k0r0pt) (sudiptosarkar@visioplanet.org).
 * @since 12 Oct, 2017 6:08 PM
 */
public class RomZeroDecoder {

  public static String decodePassword(byte[] abBuff) {
    char abDecomp[] = null;
    for (int iNdx = 3; iNdx < abBuff.length; iNdx++) {
      if (abBuff[iNdx - 3] == (byte) 0xCE && abBuff[iNdx - 2] == (byte) 0xED && abBuff[iNdx - 1] == (byte) 0xDB && abBuff[iNdx] == (byte) 0xDB) {
        char abBuff2[] = new char[abBuff.length - iNdx];
        for (int iNdx2 = iNdx - 3, iNdx3 = 0; iNdx3 < abBuff.length - iNdx; iNdx2++, iNdx3++) {
          abBuff2[iNdx3] = (char) (abBuff[iNdx2] & 0xFF);
        }
        abDecomp = decompress(abBuff2);
        break;
      }
    }

    for (char anAbDecomp : abDecomp) {
      if (anAbDecomp < 32 || anAbDecomp > 0x7E) {
        System.out.print('.');
      } else {
        System.out.print(anAbDecomp);
      }
    }

    int PASS_OFFSET = 0x14;
    StringBuilder pass = new StringBuilder();
    int i = PASS_OFFSET;
    while ((abDecomp[i] > 32) && (abDecomp[i] < 0x7E)) {
      pass.append(abDecomp[i]);
      i++;
    }

    System.out.println("The password is: " + pass);

    return "".equals(pass.toString()) ? null : pass.toString();
  }

  private static char[] decompress(char abBuff[]) {
    List<Character> mylist = new ArrayList<>();
    int iNdx = 0;
    int unknown = abBuff[iNdx++] << 24 | abBuff[iNdx++] << 16 | abBuff[iNdx++] << 8 | abBuff[iNdx++];
    int majorVersion = abBuff[iNdx++] << 8 | abBuff[iNdx++];
    int minorVersion = abBuff[iNdx++] << 8 | abBuff[iNdx++];
    int blockSize = abBuff[iNdx++] << 24 | abBuff[iNdx++] << 16 | abBuff[iNdx++] << 8 | abBuff[iNdx++];
    while (iNdx < abBuff.length) {
      int orgSize = abBuff[iNdx++] << 8 | abBuff[iNdx++];
      int rawSize = abBuff[iNdx++] << 8 | abBuff[iNdx++];
      char abCompress[] = new char[rawSize];
      for (int iNdx2 = iNdx, iNdx3 = 0; iNdx3 < abCompress.length; iNdx2++, iNdx3++) {
        try {
          abCompress[iNdx3] = (char) (abBuff[iNdx2] & 0xFF);
        } catch (ArrayIndexOutOfBoundsException a) {
          break;
        }
      }
      List<Character> l;
      try {
        l = decomp(abCompress);
      } catch (ArrayIndexOutOfBoundsException a) {
        break;
      }
      mylist.addAll(l);
      iNdx += rawSize;
    }
    char abRet[] = new char[mylist.size()];
    iNdx = 0;
    for (char b : mylist) {
      abRet[iNdx++] = b;
    }
    return abRet;
  }

  private static List<Character> decomp(char[] abBuff) {
    List<Character> objRet = new ArrayList<>();
    CircularFifoQueue<Character> window = new CircularFifoQueue<>(2048);
    BitReader objBitRead = new BitReader(abBuff);
    for (int iNdx = 0; iNdx < 2048; iNdx++) {
      window.add('\0');
    }
    while (true) {
      int bit = objBitRead.readBit();
      if (bit == 0) {
        int character = objBitRead.readByte();
        objRet.add((char) character);
        window.add((char) character);
      } else {
        int offset;
        bit = objBitRead.readBit();
        if (bit == 1) {
          offset = (int) objBitRead.readBit(7);
          if (offset == 0) {
            //end of file
            break;
          }
        } else {
          offset = (int) objBitRead.readBit(11);
        }
        int len;
        int lenField = (int) objBitRead.readBit(2);
        if (lenField < 3) {
          len = lenField + 2;
        } else {
          lenField <<= 2;
          lenField += (int) objBitRead.readBit(2);
          if (lenField < 15) {
            len = (lenField & 0x0f) + 5;
          } else {
            int lenCounter = 0;
            lenField = (int) objBitRead.readBit(4);
            while (lenField == 15) {
              lenField = (int) objBitRead.readBit(4);
              lenCounter++;
            }
            len = 15 * lenCounter + 8 + lenField;
          }
        }
        for (int i = 0; i < len; i++) {
          char character = window.get(offset);
          objRet.add(character);
          window.add(character);
        }
      }
    }
    return objRet;
  }
}
