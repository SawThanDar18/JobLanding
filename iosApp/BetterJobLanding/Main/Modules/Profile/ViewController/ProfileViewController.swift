//
//  ProfileViewController.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import UIKit

class ProfileViewController: BaseViewController {
    @IBOutlet weak var register: UIButton!
    @IBOutlet weak var profileLabel: UILabel!
    @IBOutlet weak var settingImageView: UIImageView!
    var router: ProfileRouting?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupProfile()
    }
    
    private func setupProfile(){
        self.profileLabel.text = "Profile"
        self.profileLabel.font = .poppinsSemiBold(ofSize: 24)
        self.profileLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        
        self.register.setTitle("Register Now", for: .normal)
        self.register.titleLabel?.font = .poppinsSemiBold(ofSize: 16)
        self.register.titleLabel?.tintColor = BHRJobLandingColors.bhrBJPrimary
    }

    @IBAction func handleRegisterAction(_ sender: Any) {
        router?.goToRegister()
    }
   
}
