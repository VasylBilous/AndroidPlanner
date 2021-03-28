using FluentValidation;
using Planner.Application.Validators;

namespace Planner.Application.Account.Login
{
	public class LoginValidation : AbstractValidator<LoginCommand>
	{
		public LoginValidation()
		{
			RuleFor(x => x.Email).NotEmpty().EmailAddress();
			RuleFor(x => x.Password).NotEmpty().Password();
		}
	}
}
