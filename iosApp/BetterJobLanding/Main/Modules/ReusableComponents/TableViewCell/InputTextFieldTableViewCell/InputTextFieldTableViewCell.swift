//
//  InputTextFieldTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 27/06/2024.
//

import UIKit

class InputTextFieldTableViewCell: UITableViewCell {
    
    @IBOutlet weak var inputTitleLabel: UILabel!
    @IBOutlet weak var inputTextField: UITextField!
    @IBOutlet weak var inputTextFieldImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupInputTextFieldStyle()
    }

    func setupInputTextFieldStyle(){
        self.inputTitleLabel.font = .poppinsRegular(ofSize: 14)
        self.inputTitleLabel.textColor = BHRJobLandingColors.bhrBJGray4A
        
        self.inputTextField.layer.cornerRadius = 4
        self.inputTextField.layer.borderColor = BHRJobLandingColors.bhrBJGrayC5.cgColor
        self.inputTextField.layer.borderWidth = 1
        
        self.inputTextField.placeholder = "Enter text here"
        self.inputTextField.borderStyle = .roundedRect
                // Change the text color
        self.inputTextField.textColor = UIColor.red
                // Change the font
        self.inputTextField.font = .poppinsRegular(ofSize: 14)
        
//        self.inputTextField.attributedPlaceholder = NSAttributedString(string: "", attributes: [NSAttributedString.Key.foregroundColor: UIColor.white])
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
