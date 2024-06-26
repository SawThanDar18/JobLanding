//
//  RegisterViewController.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 24/06/2024.
//

import UIKit
import shared

class RegisterViewController: BaseViewController {
    @IBOutlet weak var registerLabel: UILabel!
    @IBOutlet weak var alreadyAccountLabel: UILabel!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var registerTableView: UITableView!
    let formFields  : [RegisterFormFields] = [.phoneno, .otp]
    var router: RegisterRouting?
    var registerViewModel: RegisterViewModel?
    var inputPhoneNumber: String = ""
    var inputOTP: String = "" {
            didSet {
                print("number was set to \(inputOTP)")
            }
        }
    
    var clickSendOTP: Bool = false
    var secondsRemaining = 60
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.isPresentViewController = true
        setupRegister()
        setupRegisterTableView()
    }
    
    func sendOTPByiOS(){
        registerViewModel = DIHelperClient.shared.getRegisterViewModel()
//        registerViewModel?.requestOTP(phoneNumber: <#T##String#>)
//        registerViewModel.observeUiState{
//            uiStateData in
//            let homeUIlist : [HomeUIModel] = uiStateData.jobLandingSectionsList as [HomeUIModel] ?? []
//            self.homeUIViewModel = homeUIlist
//            homeUIlist.forEach{homelist in
//                if homelist.collectionType == HomeCollectionType.jobCollection.rawValue{
//                    self.jobsListUIModel = homelist.jobs ?? []
//                }else if homelist.collectionType == HomeCollectionType.companyCollection.rawValue{
//                    self.companyListModel = homelist.collectionCompanies
//                }
//            }
//            self.homeTableView.reloadData()
//        }
    }
    
    private func setupRegisterTableView() {
        registerTableView.dataSource = self
        registerTableView.delegate = self
        
        registerTableView.backgroundColor = .clear
        
        registerTableView.separatorStyle = .none
        registerTableView.showsVerticalScrollIndicator = false
        registerTableView.showsHorizontalScrollIndicator = false
        registerTableView.bounces = false
        registerTableView.allowsSelection = false
        
        registerTableView.contentInset = .zero
        
        registerTableView.registerForCells(
            String(describing: PhoneNumberTableViewCell.self),
            String(describing: OTPTableViewCell.self)
        )
    }
    
    func setupRegister(){
        
        self.inputPhoneNumber = ""
        
        self.registerLabel.text = "Register"
        self.registerLabel.font = .poppinsSemiBold(ofSize: 32)
        self.registerLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        
        self.alreadyAccountLabel.font = .poppinsRegular(ofSize: 14)
        self.alreadyAccountLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.alreadyAccountLabel.text = "Already have an account?"
        
        self.loginButton.setTitleColor(BHRJobLandingColors.bhrBJPrimary, for: .normal)
        self.loginButton.titleLabel?.font = .poppinsSemiBold(ofSize: 14)
        self.loginButton.setTitle("log in", for: .normal)
        
        self.nextButton.setButtonDisableStyle()
        self.nextButton.titleLabel?.text = "Next"
    }
}

extension RegisterViewController{
    @objc func handleSendOTPRequestAction(){
        //Send_OTP_Request
        self.clickSendOTP = true
        self.registerTableView.reloadData()
        self.registerViewModel?.requestOTP(phoneNumber: self.inputPhoneNumber)
    
//        registerViewModel?.observeUiStateForRequestOTP(onChange: { uistate in
//            print(uistate.description)
//        })
//        let temp = self.registerViewModel?.registerUiState
//
//               temp?.collect(collector: { state in
//                   switch state {
//                   case let success as ApiResponseStateSuccess<NSString>:
//                       // Handle success state
//                       let data = success.data
//                       print("Success: \(data)")
//                   case let error as ApiResponseStateError:
//                       // Handle error state
//                       let exception = error.exception
//                       print("Error: \(exception)")
//                   default:
//                       break
//                   }
//               }) { completion in
//                   // Handle completion if needed
//               }
    }
}
