//
//  UITableView+Extension.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import Foundation
import UIKit

extension UITableView {
    func registerForCells(_ strIDs : String...) {
        strIDs.forEach { (strID) in
            register(UINib(nibName: strID, bundle: nil), forCellReuseIdentifier: strID)
        }
    }
}
