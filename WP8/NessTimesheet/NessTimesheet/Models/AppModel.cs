using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NessTimesheet.Models
{
    public class AppModel
    {
        public Team[] Teams { get; set; }
        public Project[] Projects { get; set; }
        public Activity[] Activities { get; set; }
    }
}
