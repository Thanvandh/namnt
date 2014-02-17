using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace WebApiBluesea
{
    /// <summary>
    /// Summary description for ReceiveWebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class ReceiveWebService : System.Web.Services.WebService
    {
        readonly log4net.ILog log = log4net.LogManager.GetLogger("request_response");
        readonly log4net.ILog minsertFailedLog = log4net.LogManager.GetLogger("insertfailed");
        [WebMethod]
        public int receivemoservice(string User_ID, string Service_ID, string Command_Code, string Message, string Request_ID)
        {


            log.Info(HttpContext.Current.Request.UserHostAddress + "|" + User_ID + "|" + Service_ID + "|" + Command_Code + "|" + Message + "|" + Request_ID );
            return 0;
        }
    }
}
