//
//  DeviceLocation.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 05/06/2024.
//

import UIKit

class DeviceLocation: UIView {

    @IBOutlet var mainView: UIView!
    @IBOutlet weak var bgView: UIView!
    @IBOutlet weak var accessLocationLabel: UILabel!
    @IBOutlet weak var allowAllTheTimeButton: UIButton!
    @IBOutlet weak var allowOnlyAppUseButton: UIButton!
    @IBOutlet weak var denyButton: UIButton!
   
    required init?(coder: NSCoder){
        super.init(coder: coder)
    }
    
    override init(frame: CGRect){
        super.init(frame: frame)
        xibSetup(frame: CGRect(x: 0, y: 0, width: frame.width, height: frame.height))
        bgView.layer.cornerRadius = 10
        bgView.layer.masksToBounds = true
        mainView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        setupDeviceLocation()
    }
    
    func xibSetup(frame: CGRect){
        let view = loadXib()
        view.frame = frame
        addSubview(view)
    }
    
    func loadXib() -> UIView{
        let bundle = Bundle(for: type(of: self))
        let nib = UINib(nibName: "DeviceLocation", bundle: bundle)
        let view = nib.instantiate(withOwner: self,options: nil).first as? UIView
        return view!
    }
    
    private func setupDeviceLocation(){
        bgView.layer.cornerRadius = 8
        bgView.layer.masksToBounds = true
        accessLocationLabel.numberOfLines = 0
        accessLocationLabel.lineBreakMode = .byWordWrapping
//        accessLocationLabel.font = .sourceSansProSemibold(ofSize: 18)
        
//        allowAllTheTimeButton.setTitle(<#T##title: String?##String?#>, for: <#T##UIControl.State#>)
//        subTitleLabel.textColor = .dark1
//        subTitleLabel.font = .sourceSansProLight(ofSize: 14)
//
//        cancelButton.titleLabel?.tintColor = .white
//        cancelButton.buttonStyle = .buttonWithButtonBGColor
//        cancelButton.layer.cornerRadius = 8
//        cancelButton.titleLabel?.font = .sourceSansProSemibold(ofSize: 13)
//
//        createButton.titleLabel?.tintColor = .white
//        createButton.buttonStyle = .buttonWithPrimaryBlueBGColor
//        createButton.layer.cornerRadius = 8
//        createButton.titleLabel?.font = .sourceSansProSemibold(ofSize: 13)
    }
    
}
