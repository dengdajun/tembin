package com.base.utils.httpclient;

import org.apache.http.client.HttpClient;

/**
 * Created by wula on 2014/7/6.
 */
public class TestClient {
    private String dd;
    private String bb;

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }


    public static void main(String[] args) throws Exception {


        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String res= HttpClientUtil.post(httpClient, "http://localhost:8080/xsddWeb/receiveNoti.do", "es==", "UTF-8", null);
        //String res= HttpClientUtil.post(httpClient, "http://localhost:8080/xsddWeb/receiveNoti.do", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <soapenv:Header>  <ebl:RequesterCredentials soapenv:mustUnderstand=\"0\" xmlns:ns=\"urn:ebay:apis:eBLBaseComponents\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\">   <ebl:NotificationSignature xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\">aVOC16RvDmHgMZIzW4Ljfw==</ebl:NotificationSignature>  </ebl:RequesterCredentials> </soapenv:Header> <soapenv:Body>  <GetMyMessagesResponse xmlns=\"urn:ebay:apis:eBLBaseComponents\">   <Timestamp>2015-02-09T01:17:18.572Z</Timestamp>   <Ack>Success</Ack>   <CorrelationID>429384848230</CorrelationID>   <Version>907</Version>   <Build>E907_INTL_APIMSG_17363511_R1</Build>   <NotificationEventName>MyMessageseBayMessage</NotificationEventName>   <RecipientUserID>jazz-ren</RecipientUserID>   <EIASToken>nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ==</EIASToken>   <Messages>    <Message>     <Sender>eBay</Sender>     <RecipientUserID>jazz-ren</RecipientUserID>     <Subject>編號為 5078090082 的個案現已結束</Subject>     <MessageID>64730817884</MessageID>     <Text>&lt;![CDATA[ &lt;html&gt;&lt;head&gt;&lt;/head&gt;&lt;body&gt;&lt;div id=&quot;Header&quot;&gt;&lt;div&gt;&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td width=&quot;100%&quot; style=&quot;word-wrap:break-word&quot;&gt;&lt;table cellpadding=&quot;2&quot; cellspacing=&quot;3&quot; border=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td width=&quot;1%&quot; nowrap=&quot;nowrap&quot;&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/logos/ebay_95x39.gif&quot; height=&quot;39&quot; width=&quot;95&quot; alt=&quot;eBay&quot;&gt;&lt;/td&gt;&lt;td align=&quot;left&quot; valign=&quot;bottom&quot;&gt;&lt;span style=&quot;font-weight:bold; font-size:xx-small; font-family:verdana, sans-serif; color:#666&quot;&gt;&lt;b&gt;這則訊息是由 eBay 寄給  任青（jazz-ren）。&lt;/b&gt;&lt;br&gt;&lt;/span&gt;&lt;span style=&quot;font-size:xx-small; font-family:verdana, sans-serif; color:#666&quot;&gt;為了證明此電郵是從 eBay 寄出，我們會在電郵中顯示您的註冊姓名。 &lt;a href=&quot;http://pages.ebay.com.hk/help/confidence/name-userid-emails.html&quot;&gt;進一步了解&lt;/a&gt;。&lt;/span&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/div&gt;&lt;/div&gt;&lt;div id=&quot;Title&quot;&gt;&lt;div&gt;&lt;table style=&quot;background-color:#ffe680&quot; border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td width=&quot;8&quot; valign=&quot;top&quot;&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/globalAssets/ltCurve.gif&quot; height=&quot;8&quot; width=&quot;8&quot; alt=&quot;&quot;&gt;&lt;/td&gt;&lt;td valign=&quot;bottom&quot; width=&quot;100%&quot;&gt;&lt;span style=&quot;font-weight:bold; font-size:14pt; font-family:arial, sans-serif; color:#000; margin:2px 0 2px 0&quot;&gt;此個案已結束&lt;/span&gt;&lt;/td&gt;&lt;td width=&quot;8&quot; valign=&quot;top&quot; align=&quot;right&quot;&gt;&lt;img src=&quot;http://p.ebaystatic.com/aw/pics/globalAssets/rtCurve.gif&quot; height=&quot;8&quot; width=&quot;8&quot; alt=&quot;&quot;&gt;&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;background-color:#fc0&quot; colspan=&quot;3&quot; height=&quot;4&quot;&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/div&gt;&lt;/div&gt;&lt;div id=&quot;SingleItemCTA&quot;&gt;&lt;div&gt;&lt;table border=&quot;0&quot; cellpadding=&quot;2&quot; cellspacing=&quot;3&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td&gt;&lt;font style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot;&gt;jazz-ren，您好：&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/s.gif&quot; height=&quot;10&quot; alt=&quot; &quot;&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;eBay 提出個案的原因是因為您賣出的物品出現問題，我們審查了個案，決定向買家發出 US $15.39 的退款，而同時不會對您造成影響。該筆退款包括購物金額及原本運費。&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/s.gif&quot; height=&quot;10&quot; alt=&quot; &quot;&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;div&gt;此個案已結束，您毋需向買家或 eBay 退款，而此個案不會計入您的賣家表現評估。&lt;br&gt;&lt;br&gt; 要&lt;a&gt;查看個案的詳細資料&lt;/a&gt;，請前往調解中心。&lt;br&gt;&lt;br&gt;&lt;/div&gt;&lt;/font&gt;&lt;div&gt;&lt;table width=&quot;100%&quot; cellpadding=&quot;0&quot; cellspacing=&quot;3&quot; border=&quot;0&quot;&gt;&lt;tr&gt;&lt;td valign=&quot;top&quot; align=&quot;center&quot; width=&quot;100&quot; nowrap=&quot;nowrap&quot;&gt;&lt;img src=&quot;http://i.ebayimg.com/00/s/NjAwWDgwMA==/z/KTEAAOxycD9TTlY8/$_0.JPG?set_id=880000500F&quot; alt=&quot;3 pcs  Replacement Shaver Head Blades HQ5 for Philips Norelco Electric Razor 580&quot;&gt;&lt;/td&gt;&lt;td colspan=&quot;2&quot; valign=&quot;top&quot;&gt;&lt;table width=&quot;100%&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; border=&quot;0&quot;&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; colspan=&quot;2&quot;&gt;&lt;b&gt;3 pcs  Replacement Shaver Head Blades HQ5 for Philips Norelco Electric Razor 580&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;物品編號：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;151280316810&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;成交價：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;US $13.41&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;數目：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;1&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;成交日期：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;2015-01-29 11:19:53&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;買家：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;kch2572&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;個案編號：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;5078090082&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;個案已提出：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;Feb-08-15 13:16:09 PST&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; width=&quot;15%&quot; nowrap=&quot;nowrap&quot; valign=&quot;top&quot;&gt;個案已結束：&lt;/td&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; valign=&quot;top&quot;&gt;Feb-08-15 17:17:13 PST&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td style=&quot;font-size:10pt; font-family:arial, sans-serif; color:#000&quot; colspan=&quot;2&quot;&gt;&lt;a href=&quot;http://rover.ebay.com/rover/0/e12561.m43.l1123/7?euid=8a77fa3dd42a4a4fb185f8fa44003b8b&amp;amp;loc=http%3A%2F%2Fcgi.ebay.com%2Fws%2FeBayISAPI.dll%3FViewItemVersion%26view%3Dall%26item%3D151280316810%26tid%3D1203269756005%26ssPageName%3DADME%3AX%3ACPSCFS%3AUS%3A1123&quot; target=&quot;_blank&quot;&gt;查看已購買的物品&lt;/a&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/div&gt;&lt;td valign=&quot;top&quot; width=&quot;185&quot;&gt;&lt;div&gt;&lt;span style=&quot;font-weight:bold; font-size:10pt; font-family:arial, sans-serif; color:#000&quot;&gt;&lt;/span&gt;&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/s.gif&quot; height=&quot;4&quot; alt=&quot; &quot;&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;a href=&quot;http://rover.ebay.com/rover/0/e12561.m44.l1495/7?euid=8a77fa3dd42a4a4fb185f8fa44003b8b&amp;amp;loc=http%3A%2F%2Fres.ebay.com%2Fws%2FeBayISAPI.dll%3FResolveCPSCase%26caseId%3D5078090082%26ssPageName%3DADME%3AX%3ACPSCFS%3AHK%3A1495&quot; title=&quot;http://rover.ebay.com/rover/0/e12561.m44.l1495/7?euid=8a77fa3dd42a4a4fb185f8fa44003b8b&amp;amp;loc=http%3A%2F%2Fres.ebay.com%2Fws%2FeBayISAPI.dll%3FResolveCPSCase%26caseId%3D5078090082%26ssPageName%3DADME%3AX%3ACPSCFS%3AHK%3A1495&quot;&gt;&lt;img src=&quot;http://q.ebaystatic.com/aw/pics/buttons/emails/btnViewDetails.gif&quot; border=&quot;0&quot; height=&quot;32&quot; width=&quot;120&quot;&gt;&lt;/img&gt;&lt;/a&gt;&lt;br&gt;&lt;/div&gt;&lt;/td&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;br&gt;&lt;/div&gt;&lt;/div&gt;&lt;div id=&quot;Footer&quot;&gt;&lt;div&gt;&lt;hr style=&quot;HEIGHT: 1px&quot;&gt;&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td width=&quot;100%&quot;&gt;&lt;font style=&quot;font-size:8pt; font-family:arial, sans-serif; color:#000000&quot;&gt;電郵參考編號：[#8a77fa3dd42a4a4fb185f8fa44003b8b#]&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;br&gt;&lt;/div&gt;&lt;hr style=&quot;HEIGHT: 1px&quot;&gt;&lt;table border=&quot;0&quot; cellpadding=&quot;0&quot; cellspacing=&quot;0&quot; width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td width=&quot;100%&quot;&gt;&lt;font style=&quot;font-size:xx-small; font-family:verdana; color:#666&quot;&gt;&lt;a href=&quot;http://pages.ebay.com.hk/education/spooftutorial/index.html&quot;&gt;進一步了解&lt;/a&gt;如何保護自己的權益，辨識假冒電郵。&lt;br&gt;&lt;br&gt;這封電郵是由 eBay 寄給 renqing899@gmail.com，內容是關於您在 &lt;a href=&quot;http://www.ebay.com.hk&quot;&gt;www.ebay.com.hk&lt;/a&gt; 的註冊帳戶。&lt;br&gt;&lt;br&gt;eBay 根據您帳戶的偏好設定寄送這些電郵，如果不想收到類似的電郵，請變更您的「&lt;a href=&quot;http://my.ebay.com.hk/ws/eBayISAPI.dll?MyEbayBeta&amp;amp;CurrentPage=MyeBayNextNotificationPreferences&quot;&gt;通訊偏好設定&lt;/a&gt;」。請注意：資料更新可能需要 14 日的時間，如有任何疑問，請參閱&lt;a href=&quot;http://pages.ebay.com.hk/help/policies/privacy-policy.html&quot;&gt;私隱權政策&lt;/a&gt;與&lt;a href=&quot;http://pages.ebay.com.hk/help/policies/user-agreement.html&quot;&gt;會員合約&lt;/a&gt;。&lt;br&gt;&lt;br&gt;版權 (c) 2015 eBay Inc. 本公司保留所有權利。 所有商標與品牌皆為各自擁有者的財產。 eBay 與 eBay 標誌都是 eBay Inc. 的商標。 eBay International AG 的地址是：Helvetiastrasse 15/17 - P.O. Box 133, 3000 Bern 6,Switzerland  &lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;img src=&quot;http://rover.ebay.com/roveropen/0/e12561/7?euid=8a77fa3dd42a4a4fb185f8fa44003b8b&amp;amp;&quot; height=&quot;1&quot; width=&quot;1&quot;&gt;&lt;/div&gt;&lt;/body&gt;&lt;/html&gt; ]]&gt;</Text>     <Flagged>false</Flagged>     <Read>false</Read>     <ReceiveDate>2015-02-09T01:17:17.000Z</ReceiveDate>     <ExpirationDate>2015-03-11T01:17:17.000Z</ExpirationDate>     <ItemID>151280316810</ItemID>     <ResponseDetails>      <ResponseEnabled>false</ResponseEnabled>     </ResponseDetails>     <Folder>      <FolderID>0</FolderID>     </Folder>     <Replied>false</Replied>     <ItemEndTime>2015-02-10T10:07:29.000Z</ItemEndTime>     <ItemTitle>3 pcs  Replacement Shaver Head Blades HQ5 for Philips Norelco Electric Razor 580</ItemTitle>    </Message>   </Messages>  </GetMyMessagesResponse> </soapenv:Body></soapenv:Envelope>", "UTF-8", null);
        System.out.println(res);
        if (1==1){return;}


        /*String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetCategorySpecificsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**QM20Uw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<CategorySpecificsFileInfo>true</CategorySpecificsFileInfo>" +
                "</GetCategorySpecificsRequest>​";


        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            *//*headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));*//*
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));
        headers.add(new BasicHeader("X-EBAY-API-SITEID","77"));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", APINameStatic.GetCategorySpecifics));

        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml, "UTF-8", headers);
        System.out.println(res);*/
    }






}
