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

package org.koreops.routers.romdecoder.utils;

/**
 * Bit Reader utility.
 *
 * @author etmatix <a href="https://github.com/etmatrix/">Etmatrix</a>
 * @author Sudipto Sarkar (k0r0pt) (sudiptosarkar@visioplanet.org).
 * @since 12 Oct, 2017 6:11 PM
 */
public class BitReader {
  private final char[] buffer;
  private int bitIndex;

  public BitReader(char[] b) {
    buffer = b;
    bitIndex = 0;
  }

  public short readBit() {
    return readBitNdx(bitIndex++);
  }

  // Max return size 64 bit;
  public long readBit(int iCount) {
    long lRes = 0;
    for (int i = 0; i < iCount; i++) {
      lRes <<= 1;
      lRes |= (readBit() & 0x1);
    }
    return lRes;
  }

  public short readByte() {
    return (short) readBit(8);
  }

  private short readBitNdx(int iNdx) {
    int iByte = iNdx / 8;
    int iBit = iNdx % 8;

    if (iByte >= buffer.length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return (short) (((buffer[iByte] & (1 << (7 - iBit))) == 0) ? 0 : 1);
  }
}
