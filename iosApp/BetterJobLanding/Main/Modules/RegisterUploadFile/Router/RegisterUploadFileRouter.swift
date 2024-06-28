//
//  RegisterUploadFileUploadFileRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 26/06/2024.
//

import Foundation
import UIKit

protocol RegisterUploadFileRouting: AnyObject {
    func goToScreen()
}

class RegisterUploadFileRouter: RegisterUploadFileRouting {
    weak var viewController: RegisterUploadFileViewController?
    
    func goToScreen(){
       print("GO GO GO")
        viewController?.navigationController?.pushViewController(UIViewController(), animated: true)
        
    }
}
