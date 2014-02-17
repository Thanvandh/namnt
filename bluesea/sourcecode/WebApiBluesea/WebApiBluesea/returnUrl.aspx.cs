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
            log.Info("returnurl|" + HttpContext.Current.Request.UserHostAddress + "|" + HttpContext.Current.Request.Url.AbsoluteUri);
            Response.Write("Ketqua");
        }
    }
}