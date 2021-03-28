using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Planner.Application.Account;
using Planner.Application.Account.Login;
using Planner.Application.Account.Registration;
using System.Threading.Tasks;

namespace Planner.WebApi.Controllers
{
    [AllowAnonymous]
    public class AccountController : BaseController
    {
        [HttpPost("login")]
        public async Task<ActionResult<UserViewModel>> LoginAsync(LoginCommand command)
        {
            return await Mediator.Send(command);
        }

        [HttpPost("registration")]
        public async Task<ActionResult<UserViewModel>> RegistrationAsync(RegistrationCommand command)
        {
            return await Mediator.Send(command);
        }
    }
}
