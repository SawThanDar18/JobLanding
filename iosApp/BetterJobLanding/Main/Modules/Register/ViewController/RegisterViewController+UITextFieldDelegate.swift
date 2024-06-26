//
//  RegisterViewController+UITextFieldDelegate.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 25/06/2024.
//

import Foundation
import UIKit

extension RegisterViewController: UITextFieldDelegate{
    // UITextFieldDelegate method: called when text field begins editing
    func textFieldDidBeginEditing(_ textField: UITextField) {
        print("Text field did begin editing")
    }
    
    // UITextFieldDelegate method: called when text field ends editing
    func textFieldDidEndEditing(_ textField: UITextField) {
        print("Text field did end editing")
    }
    
    // UITextFieldDelegate method: called when return key is pressed
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Hide the keyboard
//        textField.resignFirstResponder()
        return true
    }
    
    // UITextFieldDelegate method: called when text changes
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let currentText = textField.text ?? ""
        let newText = (currentText as NSString).replacingCharacters(in: range, with: string)
        print("Current text: \(newText)")
        self.inputPhoneNumber = newText
        self.reloadRowForSendOTPButton()
        return true
    }
    
    func reloadRowForSendOTPButton() {
        let indexPath = IndexPath(row: 0, section: 0)
        self.registerTableView.reloadRows(at: [indexPath], with: .automatic)
       }
}


