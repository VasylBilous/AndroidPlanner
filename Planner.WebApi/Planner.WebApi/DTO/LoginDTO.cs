using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Planner.WebApi.DTO
{
    public class LoginDTO
    {
        [Required(ErrorMessage = "Email is required"), EmailAddress(ErrorMessage = "Wrong email")]
        public string Email { get; set; }

        [RegularExpression("/^.{6,}$/", ErrorMessage = "WrongPassword")]
        [Required(ErrorMessage = "Password is required")]
        public string Password { get; set; }
    }
}
