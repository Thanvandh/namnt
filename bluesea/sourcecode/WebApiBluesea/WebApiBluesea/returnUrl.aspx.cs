using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebApiBluesea
{
    public partial class returnUrl : System.Web.UI.Page
    {
        readonly log4net.ILog log = log4net.LogManager.GetLogger("request_response");
        protected void Page_Load(object sender, EventArgs e)
        {
            Crypto crypto = new Crypto();
            HttpContext context = HttpContext.Current;
            log.Info("returnurl|" + context.Request.UserHostAddress + "|" + context.Request.Url.AbsoluteUri);
            String encrypted = Request.QueryString["r"];
            String decrypted = crypto.decrypt(encrypted.Replace(' ', '+'));
            log.Info("returnurl decrypted|" + decrypted);
           // System.out.println(encrypted + " decrypted is " + decrypted );
            Response.Write( "returnurl|" + context.Request.UserHostAddress + "|" + context.Request.Url.AbsoluteUri + "\n\r Ket qua: " + decrypted);
        }
    }
}