//
//  RegisterRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 12/06/2024.
//

import Foundation

protocol RegisterRouting: AnyObject {
    func goToUploadCVAndProfile()
}

class RegisterRouter: RegisterRouting {
    weak var viewController: RegisterViewController?
    
    func goToUploadCVAndProfile(){
       print("GO GO GO")
        
    }
}
