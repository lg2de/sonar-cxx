/*
 * C++ Community Plugin (cxx plugin)
 * Copyright (C) 2010-2022 SonarOpenCommunity
 * http://github.com/SonarOpenCommunity/sonar-cxx
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.cxx.checks.metrics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import org.sonar.cxx.CxxAstScanner;
import org.sonar.cxx.checks.CxxFileTesterHelper;
import org.sonar.cxx.squidbridge.api.SourceFile;
import org.sonar.cxx.squidbridge.checks.CheckMessagesVerifier;

public class TooLongLineCheckTest {

  private final TooLongLineCheck check = new TooLongLineCheck();

  @Test
  @SuppressWarnings("squid:S2699") // ... verify contains the assertion
  public void test() throws UnsupportedEncodingException, IOException {
    check.maximumLineLength = 20;
    var tester = CxxFileTesterHelper.create("src/test/resources/checks/LineLength.cc", ".");
    SourceFile file = CxxAstScanner.scanSingleInputFile(tester.asInputFile(), check);

    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(5).withMessage("Split this 28 characters long line (which is greater than 20 authorized).")
      .next().atLine(6)
      .noMore();
  }

}
