//
//  Profile.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import UIKit

class ProfileViewController: BaseViewController {
   
    @IBOutlet weak var register: UIButton!
    var router: ProfileRouting?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupProfile()
    }
    
    private func setupProfile(){
        self.register.setTitle("Register Now", for: .normal) 
        self.register.titleLabel?.font = .poppinsSemiBold(ofSize: 16)
        self.register.titleLabel?.tintColor = BHRJobLandingColors.bhrBJPrimary
    }

    @IBAction func handleRegisterAction(_ sender: Any) {
        router?.goToRegister()
    }
   
}
