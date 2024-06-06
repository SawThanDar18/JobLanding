//
//  LandingCountryViewController.swift
//  iosApp
//
//  Created by Myint Zu on 03/06/2024.
//

import UIKit

class LandingCountry: UIViewController {
    @IBOutlet weak var selectCountryTitleLabel: UILabel!
    @IBOutlet weak var usemycurrentlocationButton: UIButton!
    @IBOutlet weak var getStartedButton: UIButton!
    @IBOutlet var cityButtons: [UIButton]!
    @IBOutlet weak var selectCountryLabel: UILabel!
    @IBOutlet weak var selectingCountryStackView: UIStackView!
    @IBOutlet weak var selectCountryView: UIView!
    @IBOutlet weak var selectedCountry: UIButton!
    @IBOutlet weak var countryDropDownImageView: UIImageView!
    var router: LandingCountryRouting?
    var deviceLocationPopup: DeviceLocation = DeviceLocation()
    let window: UIWindow? = UIApplication.shared.windows.filter {$0.isKeyWindow}.first
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupFormStyle()
        self.tappedAction()
        self.selectCountryTitleLabel.text = "Select your country"
        self.selectCountryLabel.text = "Select your country"
        self.getStartedButton.setTitle("Get Started", for: .normal)
        }
    
    func setupFormStyle(){
        countryDropDownImageView.isHidden = false
        self.selectingCountryStackView.layer.cornerRadius = 8
        self.selectingCountryStackView.layer.borderColor = #colorLiteral(red: 0.8941176471, green: 0.9058823529, blue: 0.9294117647, alpha: 1)
        self.selectingCountryStackView.layer.borderWidth = 1.0
        self.selectingCountryStackView.layer.masksToBounds = true
        self.selectCountryView.layer.masksToBounds = true
        self.selectCountryView.layer.borderColor = #colorLiteral(red: 0.8941176471, green: 0.9058823529, blue: 0.9294117647, alpha: 1)
        self.selectCountryView.layer.borderWidth = 1.0
        self.selectCountryView.layer.cornerRadius = 8
        self.getStartedButton.layer.cornerRadius = 8
        self.getStartedButton.layer.masksToBounds = true
    }

    
    @IBAction func handleSelection(_ sender: UIButton) {
        cityButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = !button.isHidden
                self.view.layoutIfNeeded()
            })
        }
    }
    
    enum Citys: String {
        case myanmar = "Myanmar"
        case srilanka = "Sri lanka"
        case vietnam = "Vietnam"
        case thailand = "Thailand"
    }
    
    @IBAction func cityTapped(_ sender: UIButton) {
        guard let title = sender.currentTitle, let city = Citys(rawValue: title) else {
            return
        }
        switch city {
        case .myanmar:
            selectCountryLabel.text = ""
            selectedCountry.setImage(UIImage(named: "myanmar_flag"), for: .normal)
            selectedCountry.setTitle(Citys.myanmar.rawValue, for: .normal)
        case .srilanka:
            selectCountryLabel.text = ""
            selectedCountry.setImage(UIImage(named: "srilanka_flag"), for: .normal)
            selectedCountry.setTitle(Citys.srilanka.rawValue, for: .normal)
        case .vietnam:
            selectCountryLabel.text = ""
            selectedCountry.setImage(UIImage(named: "vietnam_flag"), for: .normal)
            selectedCountry.setTitle(Citys.vietnam.rawValue, for: .normal)
//            selectCountryLabel.text = Citys.vietnam.rawValue
        case .thailand:
            selectCountryLabel.text = ""
            selectedCountry.setImage(UIImage(named: "thailand_flag"), for: .normal)
            selectedCountry.setTitle(Citys.thailand.rawValue, for: .normal)
//            selectCountryLabel.text = Citys.thailand.rawValue
        default:
            selectCountryLabel.text = ""
            selectedCountry.setImage(UIImage(named: "myanmar_flag"), for: .normal)
            selectedCountry.setTitle("Select your country", for: .normal)
//            self.selectCountryLabel.text = "Select your country"
        }
        
        cityButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = true
                self.view.layoutIfNeeded()
            })
        }
    }
    
    @IBAction func useMyCurrentLocationAction(_ sender: Any) {
        self.showAccessLocation()
    }
    
    @IBAction func getStartedAction(_ sender: Any) {
        BHRJobLandingConstants.shared.getStarted = true
        showCustomTabbar()
    }
}

extension LandingCountry{
    func showCustomTabbar(){
        let vc = CustomTabBarController()
        UIApplication.shared.windows.first?.rootViewController = vc
        UIApplication.shared.windows.first?.makeKeyAndVisible()
    }
    
    func showAccessLocation()  {
        self.deviceLocationPopup = DeviceLocation(frame: self.view.frame)
        self.deviceLocationPopup.allowAllTheTimeButton.addTarget(self, action: #selector(didTapAllowAllTheTimeAction), for: .touchUpInside)
        self.deviceLocationPopup.allowOnlyAppUseButton.addTarget(self, action: #selector(didTapAllowOnlyAppUseAction), for: .touchUpInside)
        self.deviceLocationPopup.denyButton.addTarget(self, action: #selector(didTapDenyAction),for: .touchUpInside)
        self.view.addSubview(self.deviceLocationPopup)
    }
    
    private func tappedAction(){
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(dismissPopup))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    @objc func dismissPopup(){
        cityButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = true
                self.view.layoutIfNeeded()
            })
        }
        self.deviceLocationPopup.removeFromSuperview()
    }
    
    @objc func didTapAllowAllTheTimeAction(){
        
    }
    
    @objc func didTapAllowOnlyAppUseAction(){
        
    }
    
    @objc func didTapDenyAction(){
        
    }
}
