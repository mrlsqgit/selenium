package com.kangda.appiumkangda.testcase.reservation;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.kangda.appiumkangda.base.BasePrepare;
import com.kangda.appiumkangda.pages.CommonPage;
import com.kangda.appiumkangda.pages.HomePage;
import com.kangda.appiumkangda.pages.MePage;
import com.kangda.appiumkangda.pages.ReservationPage;
import com.kangda.appiumkangda.pageshelper.ReservationPageHelper;

/**
 * @author Jone
 * @description 我的预约挂号-预约挂号下单
 * */
public class ReservationPage_001_Registration_Test extends BasePrepare{
	
	@Test(dataProvider="testData")
	public void onlineVisits(Map<String, String> data){
//		LoginPageHelper.typeloginPageInfo(appiumUtil,data.get("USERNAME"), data.get("PASSWORD"));
		appiumUtil.click(HomePage.HP_ICON_ME);
		//滚动查找我的预约挂号
		appiumUtil.scrollExact(MePage.RESERVATION);
		appiumUtil.click(MePage.MP_TEXTVIEW_RESERVATION);
		appiumUtil.click(CommonPage.CP_TEXTVIEW_ALL);
		List<WebElement> lists = appiumUtil.findElements(ReservationPage.RP_TEXTVIEW_RESERVATION);
		if (lists.size() > 0) {
			appiumUtil.click(ReservationPage.RP_TEXTVIEW_RESERVATION);
		} else {
			return;
		}
		ReservationPageHelper.registration(appiumUtil);
	}
}