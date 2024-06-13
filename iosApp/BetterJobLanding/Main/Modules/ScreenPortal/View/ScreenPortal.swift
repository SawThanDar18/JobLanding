//
//  ScreenPortal.swift
//  iosApp
//
//  Created by Myint Zu on 04/06/2024.
//

import UIKit
protocol ScreenPortalProtocol {
    func checkFirstTimeUser()
    func goToOnboarding()
    func goToLoginView()
    func goToHomeTabBar()
    func showError(error: String)
}


class ScreenPortal: UIViewController {
    let window: UIWindow? = UIApplication.shared.windows.filter {$0.isKeyWindow}.first
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        showRelatedModule()
    }
    
    func showRelatedModule(){
        if BHRJobLandingConstants.shared.getStarted{
            showCustomTabbar()
        }else{
            showLandingCountry()
            //            showHome()
        }
        
        
    }
    
    func showCustomTabbar(){
        let vc = CustomTabBarController()
        UIApplication.shared.windows.first?.rootViewController = vc
        UIApplication.shared.windows.first?.makeKeyAndVisible()
    }
    
    func showHome(){
        print("Home")
        let vc = HomeScene.create(viewModel:HomeViewModel())
        UIApplication.shared.windows.first?.rootViewController = vc
        UIApplication.shared.windows.first?.makeKeyAndVisible()
    }
    
    
    func showLandingCountry(){
        let vc = LandingCountryScene.create()
        UIApplication.shared.windows.first?.rootViewController = vc
        UIApplication.shared.windows.first?.makeKeyAndVisible()
    }
}


