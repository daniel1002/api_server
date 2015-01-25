package com.xxx.enums;

public enum ContentTypeEnum
{
  PERMISSION(
    Integer.valueOf(1)), 

  GROUP(
    Integer.valueOf(2)), 

  USER(
    Integer.valueOf(3)), 

  CONTENTTYPE(
    Integer.valueOf(4)), 

  SESSION(
    Integer.valueOf(5)), 

  SITE(
    Integer.valueOf(6)), 

  MIGRATIONHISTORY(
    Integer.valueOf(7)), 

  LOGENTRY(
    Integer.valueOf(8)), 

  TAG(
    Integer.valueOf(9)), 

  TAGGEDITEM(
    Integer.valueOf(10)), 

  SOURCE(
    Integer.valueOf(11)), 

  THUMBNAIL(
    Integer.valueOf(12)), 

  REVISION(
    Integer.valueOf(13)), 

  VERSION(
    Integer.valueOf(14)), 

  AUTHOR(
    Integer.valueOf(15)), 

  PACKAGE(
    Integer.valueOf(16)), 

  PACKAGEVERSION(
    Integer.valueOf(17)), 

  PACKAGEVERSIONSCREENSHOT(
    Integer.valueOf(18)), 

  CATEGORY(
    Integer.valueOf(19)), 

  TOPIC(
    Integer.valueOf(20)), 

  TOPICALITEM(
    Integer.valueOf(21)), 

  TIPSWORD(
    Integer.valueOf(22)), 

  PLACE(
    Integer.valueOf(23)), 

  ADVERTISEMENT(
    Integer.valueOf(24)), 

  ADVERTISEMENT_PLACES(
    Integer.valueOf(25)), 

  USERENASIGNUP(
    Integer.valueOf(26)), 

  TOKEN(
    Integer.valueOf(27)), 

  PROFILE(
    Integer.valueOf(28)), 

  PLAYER(
    Integer.valueOf(29)), 

  USEROBJECTPERMISSION(
    Integer.valueOf(30)), 

  GROUPOBJECTPERMISSION(
    Integer.valueOf(31)), 

  PACKAGE_CATEGORIES(
    Integer.valueOf(32)), 

  XTDCOMMENT(
    Integer.valueOf(33)), 

  COMMENT(
    Integer.valueOf(34)), 

  COMMENTFLAG(
    Integer.valueOf(35)), 

  CLIENTPACKAGEVERSION(
    Integer.valueOf(36)), 

  DEPENDENCY(
    Integer.valueOf(38)), 

  SETTING(
    Integer.valueOf(39)), 

  SITEPERMISSION(
    Integer.valueOf(40)), 

  PAGE(
    Integer.valueOf(41)), 

  RICHTEXTPAGE(
    Integer.valueOf(42)), 

  LINK(
    Integer.valueOf(43)), 

  BLOGPOST(
    Integer.valueOf(44)), 

  BLOGCATEGORY(
    Integer.valueOf(45)), 

  THREADEDCOMMENT(
    Integer.valueOf(46)), 

  KEYWORD(
    Integer.valueOf(47)), 

  ASSIGNEDKEYWORD(
    Integer.valueOf(48)), 

  RATING(
    Integer.valueOf(49)), 

  FORM(
    Integer.valueOf(50)), 

  FIELD(
    Integer.valueOf(51)), 

  FORMENTRY(
    Integer.valueOf(52)), 

  FIELDENTRY(
    Integer.valueOf(53)), 

  GALLERY(
    Integer.valueOf(54)), 

  GALLERYIMAGE(
    Integer.valueOf(55)), 

  REDIRECT(
    Integer.valueOf(56)), 

  SUMACTIVATEDEVICEPRODUCTPACKAGEVERSIONRESULT(
    Integer.valueOf(57)), 

  PACKAGEDIM(
    Integer.valueOf(58)), 

  SUMDOWNLOADPRODUCTRESULT(
    Integer.valueOf(59)), 

  PACKAGERANKING(
    Integer.valueOf(60)), 

  PACKAGERANKINGTYPE(
    Integer.valueOf(61)), 

  PACKAGERANKINGITEM(
    Integer.valueOf(62)), 

  FEEDBACKTYPE(
    Integer.valueOf(63)), 

  LOADINGCOVER(
    Integer.valueOf(64)), 

  IOSAPPDATA(
    Integer.valueOf(65)), 

  IOSPACKAGEVERSION(
    Integer.valueOf(66)), 

  RESOURCE(
    Integer.valueOf(67)), 

  FEEDBACK(
    Integer.valueOf(68)), 

  IOSBUYINFO(
    Integer.valueOf(69)), 

  IOSPACKAGE(
    Integer.valueOf(70)), 

  GIFTBAG(
    Integer.valueOf(71)), 

  NOTE(
    Integer.valueOf(72)), 

  RECOMMEND(
    Integer.valueOf(73)), 

  VIDEO(
    Integer.valueOf(74)), 

  ACTIVITY(
    Integer.valueOf(75)), 

  BULLETIN(
    Integer.valueOf(76)), 

  LOTTERY(
    Integer.valueOf(77)), 

  LOTTERYPRIZE(
    Integer.valueOf(78)), 

  LOTTERYWINNING(
    Integer.valueOf(79)), 

  FRIENDLINK(
    Integer.valueOf(100));

  Integer id;

  private ContentTypeEnum(Integer _id) {
    this.id = _id;
  }

  public Integer getId() {
    return this.id;
  }

  public static ContentTypeEnum getContentTypeEnum(Integer id) {
    ContentTypeEnum[] enumArr = values();
    for (ContentTypeEnum aEnum : enumArr) {
      if (aEnum.getId().intValue() == id.intValue()) {
        return aEnum;
      }
    }
    return null;
  }
}