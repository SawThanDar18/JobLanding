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
        case bhrPrimary,bhrBorder,bhrGray4A,bhrGray6A,bhrGray75,bhrGrayAA,bhrGray45,bhrWarning
    }
    
    public static let bhrBJPrimary: UIColor! = UIColor.init(named: ColorSet.bhrPrimary.rawValue)
    public static let bhrBJBorderColor: UIColor! = UIColor.init(named: ColorSet.bhrBorder.rawValue)
    public static let bhrBJGray4A: UIColor! = UIColor.init(named: ColorSet.bhrGray4A.rawValue)
    public static let bhrBJGray6A: UIColor! = UIColor.init(named: ColorSet.bhrGray6A.rawValue)
    public static let bhrBJGray75: UIColor! = UIColor.init(named: ColorSet.bhrGray75.rawValue)
    public static let bhrBJGrayAA: UIColor! = UIColor.init(named: ColorSet.bhrGrayAA.rawValue)
    public static let bhrBJGray45: UIColor! = UIColor.init(named: ColorSet.bhrGray45.rawValue)
    public static let bhrBJWarningColor: UIColor! = UIColor.init(named: ColorSet.bhrWarning.rawValue)
}
