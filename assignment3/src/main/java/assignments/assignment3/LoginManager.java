package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem; // Menginisialisasi adminSystem
        this.customerSystem = customerSystem; // Menginisialisasi customerSystem
    }

    public UserSystemCLI getSystem(String role) {
        if (role.equals("Customer")) {
            return customerSystem; // Mengembalikan customerSystem jika peran adalah "Customer"
        } else {
            return adminSystem; // Mengembalikan adminSystem jika peran bukan "Customer"
        }
    }
}
