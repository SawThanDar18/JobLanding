//
//  CellStyleHeaderTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 10/06/2024.
//

import UIKit

class CellStyleHeaderTableViewCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.titleLabel.text = "Suggested For You"
        self.titleLabel.font = .poppinsSemiBold(ofSize: 14)
        self.titleLabel.textColor = BetterJobLanding.BHRJobLandingColors.bhrBJGray6A
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
