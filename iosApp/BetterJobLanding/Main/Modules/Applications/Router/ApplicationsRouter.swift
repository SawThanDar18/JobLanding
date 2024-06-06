//
//  ApplicationsRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation

protocol ApplicationsRouting: AnyObject {
    func goToScreen()
}

class ApplicationsRouter: ApplicationsRouting {
    weak var viewController: Applications?
    
    func goToScreen(){
       print("GO GO GO")
    }
}
