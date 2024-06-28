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

//Register
enum RegisterUploadFileFormFields {
    case profileUpload, fullName, emailAddress, resumeUpload, filesAttachmentsUpload
    var info: (name:String,cellType:FormCellType) {
        switch self {
        case .profileUpload:
            return ("",.profileUpload)
        case .fullName:
            return ("",.inputTextfield)
        case .emailAddress:
            return ("",.inputTextfield)
        case .resumeUpload:
            return ("",.resumeUpload)
        case .filesAttachmentsUpload:
            return ("", .filesAttachments)
        }
    }
}


