using MediatR;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Planner.Application.Exceptions;
using Planner.Application.Interfaces;
using Planner.Domain;
using Planner.EFData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Planner.Application.Account.Login
{
    public class LoginHandler : IRequestHandler<LoginCommand, UserViewModel>
    {
        private readonly SignInManager<AppUser> _signInManager;
        private readonly UserManager<AppUser> _userManager;
        private readonly IJwtGenerator _jwtGenerator;

        public LoginHandler(SignInManager<AppUser> signInManager,
            UserManager<AppUser> userManager, IJwtGenerator jwtGenerator)
        {
            _signInManager = signInManager;
            _jwtGenerator = jwtGenerator;
            _userManager = userManager;
        }

        public async Task<UserViewModel> Handle(LoginCommand request, CancellationToken cancellationToken)
        {
            var result = await _signInManager.PasswordSignInAsync(request.Email, request.Password, false, false);

            if (!result.Succeeded)
                throw new RestException(HttpStatusCode.BadRequest, new { Email = "Login failed" });

            var user = await _userManager.FindByNameAsync(request.Email);
            await _signInManager.SignInAsync(user, isPersistent: false);

            return new UserViewModel
            {
                DisplayName = user.DisplayName,
                Token = _jwtGenerator.CreateToken(user),
                UserName = user.UserName,
                Image = null
            };
        }
    }
}
