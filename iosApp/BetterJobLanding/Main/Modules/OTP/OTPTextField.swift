//
//  OTPTextField.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 25/06/2024.
//

import Foundation
import UIKit

class OTPTextField: UITextField {
    
    weak var previousTextField: OTPTextField?
    weak var nextTextField: OTPTextField?
    var bottomBorder = UIView()
    
    var value: String? {
        self.text
    }
    
    var inputType: InputType {
        _inputType
    }
    
    private var _inputType: InputType = .general {
        didSet {
            configureTextFieldAttributes(_inputType)
        }
    }
    
    private func configureTextFieldAttributes(_ inputType: InputType) {
        
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
    
        self.translatesAutoresizingMaskIntoConstraints = false
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    override public func deleteBackward(){
        text = ""
        previousTextField?.becomeFirstResponder()
    }
}
