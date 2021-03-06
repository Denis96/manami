package io.github.manami.persistence.utility;

import static io.github.manami.dto.TestConst.UNIT_TEST_GROUP;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class VersionTest {

  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation is successful for a simple version string.")
  public void testSimpleVersionisValid() {
    // given
    final String version = "1.2.3";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation is successful for a versions with high numbers.")
  public void testVersionisValidForHighNumbers() {
    // given
    final String version = "134.212.356";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation fails for non numeric chars.")
  public void testVersionInvalidChars() {
    // given
    final String version = "1.?.3";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation fails if the version does not consists of excactly three parts.")
  public void testVersionInvalidTooManyParts() {
    // given
    final String version = "1.4.3.1";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation fails if the version does not consists of excactly three parts.")
  public void testVersionInvalidTooLessParts() {
    // given
    final String version = "1.4";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Edge case. Test that the validation fails if the parts are there, but the last number is missing.")
  public void testVersionInvalidMissingLastPart() {
    // given
    final String version = "1.4.";

    // when
    final boolean result = Version.isValid(version);

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Test that the validation fails if the string is empty or null.")
  public void testVersionInvalidEmptyOrNull() {
    // given
    final String versionWhitespace = "  ";
    final String versionEmpty = EMPTY;
    final String versionNull = null;

    // when
    final boolean resultWhitespace = Version.isValid(versionWhitespace);
    final boolean resultEmpty = Version.isValid(versionEmpty);
    final boolean resultNull = Version.isValid(versionNull);

    // then
    assertThat(resultWhitespace).isFalse();
    assertThat(resultEmpty).isFalse();
    assertThat(resultNull).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is newer by major version")
  public void testIsNewerByMajorVersion() {
    // given
    final Version version = new Version("4.0.0");

    // when
    final boolean result = version.isNewerThan("5.0.0");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by major version")
  public void testIsNotNewerByMajorVersion() {
    // given
    final Version version = new Version("5.0.0");

    // when
    final boolean result = version.isNewerThan("4.0.0");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is newer by minor version")
  public void testIsNewerByMinorVersion() {
    // given
    final Version version = new Version("1.4.0");

    // when
    final boolean result = version.isNewerThan("1.5.0");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by minor version")
  public void testIsNotNewerByMinorVersion() {
    // given
    final Version version = new Version("1.5.0");

    // when
    final boolean result = version.isNewerThan("1.4.0");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is newer by bugfix version")
  public void testIsNewerByBugfixVersion() {
    // given
    final Version version = new Version("1.2.4");

    // when
    final boolean result = version.isNewerThan("1.2.5");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by bugfix version")
  public void testIsNotNewerByBugfixVersion() {
    // given
    final Version version = new Version("1.2.5");

    // when
    final boolean result = version.isNewerThan("1.2.4");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is not newer.")
  public void testIsNotNewer() {
    // given
    final Version version = new Version("2.10.3");

    // when
    final boolean result = version.isNewerThan("2.10.2");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version throws exception if it is not valid.", expectedExceptions = IllegalArgumentException.class)
  public void testOtherVersionIsNotValid() {
    // given
    final Version version = new Version("2.10.3");

    // when
    version.isNewerThan(" ? ");
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Call of the constructor with invalid string throws exception.", expectedExceptions = IllegalArgumentException.class)
  public void testCreationFails() {
    // given
    final String version = "1.?.2";

    // when
    new Version(version);
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Instance creation is working with valid string.")
  public void testCreationWorks() {
    // given
    final String version = "1.2.2";

    // when
    final Version result = new Version(version);

    // then
    assertThat(result).isNotNull();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by major version")
  public void testIsOlderByMajorVersion() {
    // given
    final Version version = new Version("4.0.0");

    // when
    final boolean result = version.isOlderThan("5.0.0");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by major version")
  public void testIsNotOlderByMajorVersion() {
    // given
    final Version version = new Version("5.0.0");

    // when
    final boolean result = version.isOlderThan("4.0.0");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by minor version")
  public void testIsOlderByMinorVersion() {
    // given
    final Version version = new Version("1.4.0");

    // when
    final boolean result = version.isOlderThan("1.5.0");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by minor version")
  public void testIsNotOlderByMinorVersion() {
    // given
    final Version version = new Version("1.5.0");

    // when
    final boolean result = version.isOlderThan("1.4.0");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by bugfix version")
  public void testIsOlderByBugfixVersion() {
    // given
    final Version version = new Version("1.2.4");

    // when
    final boolean result = version.isOlderThan("1.2.5");

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is older by bugfix version")
  public void testIsNotOlderByBugfixVersion() {
    // given
    final Version version = new Version("1.2.5");

    // when
    final boolean result = version.isOlderThan("1.2.4");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version is not older.")
  public void testIsNotOlder() {
    // given
    final Version version = new Version("2.10.3");

    // when
    final boolean result = version.isOlderThan("2.10.2");

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Check the other version throws exception if it is not valid.", expectedExceptions = IllegalArgumentException.class)
  public void testOtherVersionIsNotValidCheckingIfItsOlder() {
    // given
    final Version version = new Version("2.10.3");

    // when
    version.isOlderThan(" ? ");
  }
}