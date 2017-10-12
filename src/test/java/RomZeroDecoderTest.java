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

import org.junit.Assert;
import org.junit.Test;
import org.koreops.routers.romdecoder.RomZeroDecoder;

import java.io.File;
import java.io.FileInputStream;

/**
 * Test class for RomZeroDecoder.
 *
 * @author Sudipto Sarkar (k0r0pt) (sudiptosarkar@visioplanet.org).
 * @since 12 Oct, 2017 6:16 PM
 */
public class RomZeroDecoderTest {
  @Test
  public void testDecodePassword() throws Exception {
    File file = new File("src/test/resources/rom-0");
    FileInputStream fileInputStream = new FileInputStream(file);
    byte[] buffer = new byte[(int) file.length()];
    fileInputStream.read(buffer);
    Assert.assertEquals("3132303639", RomZeroDecoder.decodePassword(buffer));
  }
}
