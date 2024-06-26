//
//  PhoneNumberTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import UIKit

class PhoneNumberTableViewCell: UITableViewCell {
    
    @IBOutlet weak var phoneNumberTitleLabel: UILabel!
    @IBOutlet weak var countryPickerTxtField: UITextField!
    @IBOutlet weak var sendOTPButton: UIButton!
    @IBOutlet weak var phoneNumberTxtField: UITextField!
    @IBOutlet weak var lostYourPhoneNoButton: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCountryPicker()
        setupPhoneNumberTxtFieldStyle()
    }
    
    func setupCountryPicker(){
//        let cpv = CountryPickerView(frame: CGRect(x: 0, y: 0, width: 120, height: 20))
//                countryPickerTxtField.leftView = cpv
//        countryPickerTxtField.leftViewMode = .always
    }
    
    func setupPhoneNumberTxtFieldStyle(){
        // Create the full text string
        let fullText = "Your phone number*"
        
        // Create an NSMutableAttributedString from the full text
        let attributedString = NSMutableAttributedString(string: fullText)
        
        // Define the range of text you want to change color and font
        let range = (fullText as NSString).range(of: "*")
        
        
        // Combine both font and color attributes
        let combinedAttributes: [NSAttributedString.Key: Any] = [
            .font: UIFont.poppinsLight(ofSize: 5),
            .foregroundColor: UIColor.orange
        ]
        
        // Add the combined attributes to the specified range
        attributedString.addAttributes(combinedAttributes, range: range)
        
        // Assign the attributed string to the label
        self.phoneNumberTitleLabel.attributedText = attributedString
        
        
        self.countryPickerTxtField.layer.borderColor = BHRJobLandingColors.bhrBJGrayC5.cgColor
        self.countryPickerTxtField.layer.borderWidth = 1
        self.countryPickerTxtField.layer.cornerRadius = 8
        self.countryPickerTxtField.textColor = BHRJobLandingColors.bhrBJGray75
        self.countryPickerTxtField.font = .poppinsRegular(ofSize: 14)
        
        self.phoneNumberTxtField.layer.borderColor = BHRJobLandingColors.bhrBJGrayC5.cgColor
        self.phoneNumberTxtField.layer.borderWidth = 1
        self.phoneNumberTxtField.layer.cornerRadius = 8
        
//        self.phoneNumberTitleLabel.font = .poppinsRegular(ofSize: 14)
//        self.phoneNumberTitleLabel.textColor = BHRJobLandingColors.bhrBJGray4A
//        self.phoneNumberTitleLabel.text = "Your phone number*"
        
        let placeholderText = "Enter your number"
        let attributedStringForTxtField = NSAttributedString(string: "", attributes: [
            .font: UIFont.poppinsRegular(ofSize: 14),
                    .foregroundColor: BHRJobLandingColors.bhrBJGrayAA
                ])
        self.phoneNumberTxtField.attributedPlaceholder = NSAttributedString(string: placeholderText)
        self.phoneNumberTxtField.attributedText = attributedStringForTxtField
        
        self.sendOTPButton.setTitle("Send OTP", for: .normal)
        self.sendOTPButton.titleLabel?.font = .poppinsRegular(ofSize: 14)
        self.sendOTPButton.setButtonDisableStyle(radius: 0)
        
        self.lostYourPhoneNoButton.setTitle("Lost your phone number?", for: .normal)
        self.lostYourPhoneNoButton.titleLabel?.tintColor = BHRJobLandingColors.bhrBJBlueDE
        self.lostYourPhoneNoButton.titleLabel?.font = .poppinsRegular(ofSize: 12)
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
