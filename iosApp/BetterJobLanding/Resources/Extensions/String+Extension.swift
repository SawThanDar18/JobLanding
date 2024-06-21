//
//  String+Extension.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 19/06/2024.
//

import Foundation
import UIKit

extension Optional where Wrapped == String {
    var orEmpty: String { self ?? "" }
}
