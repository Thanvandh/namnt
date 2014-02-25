using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using WebApiBluesea.Utils;

namespace WebApiBluesea.Controllers
{
    public class ReceiveMOController : ApiController
    {
        // POST api/receivemo
        //public string Post([FromBody]string value)
        //{
        //    Debug.WriteLine(value);
        //    return "abc";
        //}
        readonly log4net.ILog logAll = log4net.LogManager.GetLogger("request_response");
        readonly log4net.ILog logError = log4net.LogManager.GetLogger("error");
        public HttpResponseMessage Post(HttpRequestMessage request)
        {

            var requestText = request.Content.ReadAsStringAsync().Result;
            string ip = HttpRequestMessageHelper.GetClientIpAddress(request);
            logAll.Info("request|" + ip + "|" + requestText);
            string result = "0";
            logAll.Info("response|" + requestText + "|" + result);
            return new HttpResponseMessage() { Content = new StringContent(result) };

        }

        public HttpResponseMessage Get()
        {
            logAll.Info("request GET|" + HttpContext.Current.Request.UserHostAddress + "|" + HttpContext.Current.Request.Url.AbsoluteUri);
            string response = "0|Test ket noi";

            return new HttpResponseMessage() { Content = new StringContent(response) };
        }
    }
}
