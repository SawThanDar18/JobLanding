//
//  ScreenPortalScene.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import Foundation
import UIKit

enum ScreenPortalScene {
    static func create() -> UIViewController {
        let st = UIStoryboard(name: "ScreenPortal", bundle: BHRJobLandingConstants.General.bundle)
        let vc = st.instantiateViewController(withIdentifier: String(describing: ScreenPortal.self)) as! ScreenPortal
        return vc
    }
}
