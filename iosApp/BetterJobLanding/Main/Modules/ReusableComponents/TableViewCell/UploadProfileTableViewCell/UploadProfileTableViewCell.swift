//
//  UploadProfileTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 26/06/2024.
//

import UIKit

class UploadProfileTableViewCell: UITableViewCell {
    @IBOutlet weak var profileView: UIView!
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var profileUploadButton: UIButton!
    @IBOutlet weak var uploadProfilePictureLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        self.uploadProfilePictureLabel.text = "Upload profile picture*"
        self.uploadProfilePictureLabel.font = .poppinsRegular(ofSize: 14)
        self.uploadProfilePictureLabel.textColor = BHRJobLandingColors.bhrBJGray4A
        
        self.profileImageView.setRounded()
        self.profileImageView.clipsToBounds = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
