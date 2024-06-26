//
//  BaseViewController.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import UIKit

class BaseViewController: UIViewController {
    @IBOutlet weak var backMainView: UIView!
    @IBOutlet weak var backButton: UIButton!
    var isPresentViewController: Bool?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }

    func setupUI() {
        view.backgroundColor = .clear
        self.navigationController?.navigationBar.isHidden = true
        
        // Back Button
        backButton?.addTarget(self, action: #selector(handleBackAction), for: .touchUpInside)
    }
    
    @objc func handleBackAction(_ sender: UIButton) {
//        if isPresentViewController{
//            navigationController?.dismiss(animated: true)
//            return
//        }else{
//            if SettingInfo.shared.ifAfterMembershipFamilyMemberForm{
//                navigationController?.popToRootViewController(animated: true)
//                SettingInfo.shared.ifAfterMembershipFamilyMemberForm = false
//                return
//            }
//            navigationController?.popViewController(animated: true)
//        }
        if isPresentViewController ?? true{
            navigationController?.dismiss(animated: true)
            return
        }else{
            navigationController?.popToRootViewController(animated: true)
        }
    }

}
