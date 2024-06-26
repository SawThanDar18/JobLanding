//
//  RegisterViewController+UITableView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import Foundation
import UIKit

extension RegisterViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return formFields.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let registerFormFields = formFields[indexPath.row]
        switch registerFormFields.info.cellType{
        case .phoneno:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: PhoneNumberTableViewCell.self), for: indexPath) as? PhoneNumberTableViewCell else {
                return UITableViewCell()
            }
            cell.phoneNumberTxtField.delegate = self
            cell.sendOTPButton.addTarget(self, action: #selector(handleSendOTPRequestAction), for: .touchUpInside)
            if !(self.inputPhoneNumber.isEmpty){
                DispatchQueue.main.async {
                    cell.phoneNumberTxtField.becomeFirstResponder()
                }
                cell.phoneNumberTxtField.text = self.inputPhoneNumber
                cell.sendOTPButton.setButtonActiveStyle()
            }else{
                cell.phoneNumberTxtField.text = self.inputPhoneNumber
                cell.sendOTPButton.setButtonDisableStyle()
            }
            
            if clickSendOTP{
                cell.sendOTPButton.setButtonDisableStyle()
            }else{
                cell.sendOTPButton.setButtonActiveStyle()
            }
            return cell
        case .otp:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: OTPTableViewCell.self), for: indexPath) as? OTPTableViewCell else {
                return UITableViewCell()
            }
            if clickSendOTP{
                cell.secLabel.isHidden = false
                cell.secLabel.textColor = BHRJobLandingColors.bhrBJGrayC5
                cell.secLabel.font = .poppinsRegular(ofSize: 12)
                cell.secLabel.text = "\("0:59")"
                
                Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { _ in
                    if self.secondsRemaining >= 0 {
                        if self.secondsRemaining < 10{
                            cell.secLabel.text = "0:0\(self.secondsRemaining)"
                        }else{
                            cell.secLabel.text = "0:\(self.secondsRemaining)"
                        }
                        self.secondsRemaining -= 1
                    }
                    if self.secondsRemaining == 0{
                        cell.secLabel.isHidden = true
                        self.clickSendOTP = false
                        self.reloadRowForSendOTPButton()
                    }
                }
               
            }
            return cell
        default:
            return UITableViewCell()
        }
    }
}
