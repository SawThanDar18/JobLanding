//
//  OTPTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import UIKit

class OTPTableViewCell: UITableViewCell {
    
    @IBOutlet weak var otpTitleLabel: UILabel!
    @IBOutlet weak var otpView: UIView!
    @IBOutlet weak var getInsteadOTPCode: UIButton!
    @IBOutlet weak var secLabel: UILabel!
    let otpStackView = OTPStackView()
    var number: String = ""{
           didSet {
               print("number was set to \(number)")
           }
       }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        setupOTPStyle()
    }
    
    func setupOTPStyle(){
        self.secLabel.isHidden = true
        
        self.secLabel.font = .poppinsRegular(ofSize: 12)
        self.secLabel.textColor = BHRJobLandingColors.bhrBJGrayC5
        
        // Create the full text string
        let fullText = "Enter OTP code*"
        
        // Create an NSMutableAttributedString from the full text
        let attributedString = NSMutableAttributedString(string: fullText)
        
        // Define the range of text you want to change color and font
        let range = (fullText as NSString).range(of: "*")
        
        
        // Combine both font and color attributes
        let combinedAttributes: [NSAttributedString.Key: Any] = [
            .font: UIFont.poppinsLight(ofSize: 4),
            .foregroundColor: UIColor.orange
        ]
        
        // Add the combined attributes to the specified range
        attributedString.addAttributes(combinedAttributes, range: range)
        
        // Assign the attributed string to the label
        self.otpTitleLabel.attributedText = attributedString
        
        
        
        //        self.otpTitleLabel.text = "Enter OTP code"
        
        self.getInsteadOTPCode.setTitle("Get instead OTP code", for: .normal)
        self.getInsteadOTPCode.setTitleColor(BHRJobLandingColors.bhrBJGrayAA, for: .normal)
        self.getInsteadOTPCode.titleLabel?.font = .poppinsRegular(ofSize: 12)
        
        for textfiled in otpStackView.textFieldsCollection{
            textfiled.cornerRadius = CGFloat(8)
            textfiled.borderWidth = 1
            textfiled.borderColor = BHRJobLandingColors.bhrBJGrayC5
            textfiled.textColor = BHRJobLandingColors.bhrBJGrayC5
        }
        
        for textfiledcollection in otpStackView.textFieldsCollection{
            textfiledcollection.isSecureTextEntry = true
            textfiledcollection.font =  .poppinsRegular(ofSize: 14)
        }
        self.otpStackView.delegate = self
        // OTP Container
        otpView.backgroundColor = .clear
        otpView.addSubview(otpStackView)
        otpStackView.heightAnchor.constraint(equalTo: otpView.heightAnchor).isActive = true
        otpStackView.leftAnchor.constraint(equalTo: otpView.leftAnchor).isActive = true
        otpStackView.rightAnchor.constraint(equalTo: otpView.rightAnchor).isActive = true
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
}

extension OTPTableViewCell: OTPDelegate {
    
    func didChangeValidity(isValid: Bool) {
        print(self.otpStackView.getOTP())
        self.number = self.otpStackView.getOTP()
        self.otpStackView.setAllFieldColor(isWarningColor: false, color: UIColor.red)
    }
}
