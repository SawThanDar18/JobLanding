//
//  RegisterRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 12/06/2024.
//

import Foundation
import UIKit

protocol RegisterRouting: AnyObject {
    func goToCVUpload()
}

class RegisterRouter: RegisterRouting {
    weak var viewController: RegisterViewController?
    
    func goToCVUpload(){
       print("GO GO GO")
        viewController?.navigationController?.pushViewController(UIViewController(), animated: true)
        
    }
}
