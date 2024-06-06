//
//  BHRJobLandingColors.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

class BHRJobLandingColors {
    
    private enum ColorSet: String {
        case bhrPrimary,bhrGray,bhrDarkGray
    }
    
    public static let bhrBJPrimary: UIColor! = UIColor.init(named: ColorSet.bhrPrimary.rawValue)
    public static let bhrBJGray: UIColor! = UIColor.init(named: ColorSet.bhrGray.rawValue)
    public static let bhrBJDarkGray: UIColor! = UIColor.init(named: ColorSet.bhrDarkGray.rawValue)
    
    
}
