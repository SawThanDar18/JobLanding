//
//  InboxRouter.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation

protocol InboxRouting: AnyObject {
    func goToScreen()
}

class InboxRouter: InboxRouting {
    weak var viewController: Inbox?
    
    func goToScreen(){
       print("GO GO GO")
    }
}
