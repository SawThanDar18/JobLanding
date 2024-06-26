//
//  InputValidator.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 25/06/2024.
//

import Foundation

enum InputValidationError: Error {
    case `default`(msg: String)
}

class InputValidator {
    
    @discardableResult
    static func hasValidInput(input: [(String?, InputType)]) throws -> Bool {
        
        let results: [Bool] = try input.map {
            
            var isValid: Bool = InputValidator.isInputValid(input: $0.0)
            
            if $0.1 == .email {
                isValid = InputValidator.isValidEmail($0.0 ?? "")
            }
            
            if !isValid {
                throw InputValidationError.default(msg: "Invalid \($0.1.rawValue)")
            }
            
            return isValid
            
        }
        
        return !results.contains(false)
    }
    
    static func isInputValid(input: String?) -> Bool {
        guard let input = input, !input.isEmpty else { return false }
        return true
    }
    
    static func isValidEmail(_ email: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        
        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailPred.evaluate(with: email)
    }
    
    static func isValidPhone(phone: String) -> Bool {
            let phoneRegex = "^[0-9+]{0,1}+[0-9]{5,16}$"
            let phoneTest = NSPredicate(format: "SELF MATCHES %@", phoneRegex)
            return phoneTest.evaluate(with: phone)
        }
}
