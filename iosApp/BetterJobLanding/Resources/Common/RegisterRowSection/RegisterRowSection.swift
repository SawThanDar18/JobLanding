//
//  RegisterRowSection.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import Foundation

public enum InputType: String {
    case general = "input"
    case email
}

//Register
enum RegisterFormFields {
    case phoneno, otp
    var info: (name:String,cellType:FormCellType) {
        switch self {
        case .phoneno:
            return ("Your phone number*",.phoneno)
        case .otp:
            return ("Enter OTP code*",.otp)
        }
    }
}

