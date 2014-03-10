using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using WebApiBluesea.Utils;

namespace WebApiBluesea
{
    public partial class WapCharging : System.Web.UI.Page
    {
        readonly log4net.ILog log = log4net.LogManager.GetLogger("request_response");
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        protected void Button1_Click(object sender, EventArgs e)
        {
            //Label1.Text = "Clicked at " + DateTime.Now.ToString();
            Crypto crypto = new Crypto();
            string id = DateTime.UtcNow.ToString("yyyyMMddHHmmssfffffff",
                                       CultureInfo.InvariantCulture);
            String returnurl = "http://112.213.86.104:8088/returnurl.aspx|" + id + "|6|1000";
            log.Info("returnurl before encrypted :" + returnurl); 
            String encrypted = crypto.encrypt(returnurl);
            String returnurlencode = HttpUtility.UrlEncode(encrypted);
            String urlcheckout = "http://3g.topteen.vn/dt/dungfmc/?k=" + returnurlencode;
            log.Info("urlcheckout :" + urlcheckout);
            Response.Redirect(urlcheckout);
        }
    }
}