//
//  LandingCountryViewController.swift
//  iosApp
//
//  Created by Myint Zu on 03/06/2024.
//

import UIKit
import shared

struct FlagButtonData {
    //    let cityButton: UIButton!
    let countryData: CountryData
    let titleFlag: String
}


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
    var viewModel: ChooseCountryViewModel!
    var countriesList: [CountryData] = []
    var flagButtonData: [FlagButtonData] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setupFormStyle()
        self.tappedAction()
        self.fetchLandingCountry()
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
        self.selectCountryView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.selectCountryView.layer.borderWidth = 1.0
        self.selectCountryView.layer.cornerRadius = 8
        self.getStartedButton.layer.cornerRadius = 8
        self.getStartedButton.layer.masksToBounds = true
    }
    
    func fetchLandingCountry(){
        viewModel = DIHelperClient.shared.getChooseCountryViewModel()
        viewModel.getCountriesList()
        viewModel.observeUiState { uiStateData in
            let countriesList: [CountryData] = uiStateData.countries as? [CountryData] ?? []
            self.countriesList = countriesList
            countriesList.forEach { country in
                print(country.id)
                print(country.countryName)
                var currentFlag: String = ""
                if country.countryName.uppercased() == "myanmar".uppercased(){
                    currentFlag = "myanmar_flag"
                }else if country.countryName.uppercased() == "Singapore".uppercased(){
                    currentFlag = "myanmar_flag"
                }
                else if country.countryName.uppercased() == "Sri Lanka".uppercased(){
                    currentFlag = "srilanka_flag"
                }
                else if country.countryName.uppercased() == "Cambodia".uppercased(){
                    currentFlag = "myanmar_flag"
                }
                else if country.countryName.uppercased() == "vietnam".uppercased(){
                    currentFlag = "vietnam_flag"
                }
                else if country.countryName.uppercased() == "thailand".uppercased(){
                    currentFlag = "thailand_flag"
                }
                else{
                    currentFlag = "myanmar_flag"
                }
                self.flagButtonData.append(
                    FlagButtonData(countryData: country, titleFlag: currentFlag))
            }
        }
    }
    
    func fetchSelectedCountryDynamicID(selectedId: String){
        viewModel.getDynamicPagesId(countryId: selectedId)
        viewModel.observeUiState { uiStateData in
            let dynamicPageID: String = uiStateData.dynamicPageId
            print(dynamicPageID)
            if dynamicPageID != nil{
                self.viewModel.updateCountryId(countryId: dynamicPageID)
                self.viewModel.observeUiState { uiStateData in
                    print(self.viewModel.getCountryId())
                }
            }else{
                print("Dynamic Page ID Required")
            }
        }
    }
    
    @IBAction func handleSelection(_ sender: UIButton) {
        cityButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = !button.isHidden
                button.setTitle(self.flagButtonData[button.tag].countryData.countryName, for: .normal)
                button.setImage(UIImage(named: self.flagButtonData[button.tag].titleFlag), for: .normal)
                self.view.layoutIfNeeded()
            })
        }
        
    }
    
    @IBAction func cityTapped(_ sender: UIButton) {
        cityButtons.forEach { (button) in
            UIView.animate(withDuration: 0.3, animations: {
                button.isHidden = true
                self.selectCountryLabel.text = ""
                self.selectedCountry.setTitle(self.flagButtonData[sender.tag].countryData.countryName, for: .normal)
                self.selectedCountry.setImage(UIImage(named: self.flagButtonData[sender.tag].titleFlag), for: .normal)
                self.view.layoutIfNeeded()
            })
        }
        //Get dynamic page id
        fetchSelectedCountryDynamicID(selectedId: self.flagButtonData[sender.tag].countryData.id)
    }
    
    @IBAction func useMyCurrentLocationAction(_ sender: Any) {
        self.showAccessLocation()
    }
    
    @IBAction func getStartedAction(_ sender: Any) {
       
        if self.viewModel.getCountryId() != nil && self.viewModel.getCountryId() != ""{
            BHRJobLandingConstants.shared.getStarted = true
            SettingInfo.shared.selectedCountryID = self.viewModel.getCountryId()
            
            let screenPortal = ScreenPortalScene.create()
            UIApplication.shared.windows.first?.rootViewController = screenPortal
            UIApplication.shared.windows.first?.makeKeyAndVisible()
        }else{
            //Choose Country
            self.showToast(message: "Choose Country")
        }
       
    }
}

extension LandingCountry{
    
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
