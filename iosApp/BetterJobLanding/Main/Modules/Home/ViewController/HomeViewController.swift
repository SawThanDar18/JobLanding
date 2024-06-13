//
//  Home.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import UIKit
import shared

class HomeViewController: BaseViewController {
    @IBOutlet weak var homeTableView: UITableView!
    @IBOutlet weak var signUpMainView: UIView!
    @IBOutlet weak var signUpLabel: UILabel!
    @IBOutlet weak var signUpButton: UIButton!
    var viewModel: HomeViewModels!
    var router: HomeRouter?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
//       let temp = DIHelperKt.doInitKoin(localStorage: IOSLocalStorage(), fileHandler: iOSFileHandler(), assetProvider: IosAssetProvider())
//        let tt: Void = HomeRepository.getJobLandingSections(HomeRepository)
      let tete = SharedViewModel()
        print(tete.sharedData)
        
//        let test = HomeViewModel(localStorage: IOSLocalStorage(), homeRepository: homere)
//        print(
        let viewModel: HomeViewModels = DIHelperKt.getko
        KoinKt.getKoin().get()
//        let test = HomeRepository.getJobLandingSections(<#T##self: HomeRepository##HomeRepository#>)
      
        setupHomeStyle()
        configureTableView()
        self.homeTableView.addSubview(signUpMainView)
    }
    
    func setupHomeStyle(){
        self.signUpButton.tintColor = BHRJobLandingColors.bhrBJPrimary
        self.signUpButton.layer.cornerRadius = 8
        self.signUpButton.backgroundColor = UIColor.white
        self.signUpButton.titleLabel?.font = .poppinsMedium(ofSize: 12)
      
        self.signUpLabel.font = .poppinsRegular(ofSize: 12)
        self.signUpLabel.textColor = UIColor.white
        self.signUpLabel.text = "Sign up with email or phone number!"
    }
    
    func configureTableView(){
        self.homeTableView.bringSubviewToFront(signUpMainView)
        homeTableView.translatesAutoresizingMaskIntoConstraints = false
        homeTableView.dataSource = self
        homeTableView.delegate = self
        homeTableView.showsVerticalScrollIndicator = false
        homeTableView.showsHorizontalScrollIndicator = false
        homeTableView.register(UINib(nibName: String(describing: SearchTextFieldTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: SearchTextFieldTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleOneTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleOneTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleTwoTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleTwoTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleThreeTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleThreeTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleFourTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleFourTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleFiveTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleFiveTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleSixTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleSixTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleHeaderTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleHeaderTableViewCell.self))
        homeTableView.register(UINib(nibName: String(describing: CellStyleSevenTableViewCell.self), bundle: BHRJobLandingConstants.General.bundle), forCellReuseIdentifier: String(describing: CellStyleSevenTableViewCell.self))
        homeTableView.separatorStyle = .none
        homeTableView.estimatedRowHeight = 84
        
        view.addSubview(homeTableView)
       
        if #available(iOS 15.0, *) {
            homeTableView.sectionHeaderTopPadding = 0
        } else {
            // Fallback on earlier versions
        }
    }
    
    @IBAction func signUpAction(_ sender: Any) {
        router?.goToRegister()
    }
    

}
