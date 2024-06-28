//
//  RegisterUploadFileViewController.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 26/06/2024.
//

import UIKit

class RegisterUploadFileViewController: UIViewController {
    @IBOutlet weak var registerButton: UIButton!
    @IBOutlet weak var registerFileUploadTableView: UITableView!
    @IBOutlet weak var registerLabel: UILabel!
    let formFields  : [RegisterUploadFileFormFields] = [.profileUpload, .fullName, .emailAddress, .resumeUpload, .filesAttachmentsUpload]
    var router: RegisterUploadFileRouting?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupRegisterUploadFile()
        setupRegisterUploadFileTableView()
        // Do any additional setup after loading the view.
    }

    func setupRegisterUploadFile(){
        self.registerLabel.text = "Register"
        self.registerLabel.font = .poppinsSemiBold(ofSize: 32)
        self.registerLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        
        self.registerButton.setButtonDisableStyle()
        self.registerButton.titleLabel?.text = "Register"
    }

    private func setupRegisterUploadFileTableView() {
        registerFileUploadTableView.dataSource = self
        registerFileUploadTableView.delegate = self
        
        registerFileUploadTableView.backgroundColor = .clear
        
        registerFileUploadTableView.separatorStyle = .none
        registerFileUploadTableView.showsVerticalScrollIndicator = false
        registerFileUploadTableView.showsHorizontalScrollIndicator = false
        registerFileUploadTableView.bounces = false
        registerFileUploadTableView.allowsSelection = false
        
        registerFileUploadTableView.contentInset = .zero
        
        registerFileUploadTableView.registerForCells(
            String(describing: UploadProfileTableViewCell.self),
            String(describing: InputTextFieldTableViewCell.self)
        )
    }
}
