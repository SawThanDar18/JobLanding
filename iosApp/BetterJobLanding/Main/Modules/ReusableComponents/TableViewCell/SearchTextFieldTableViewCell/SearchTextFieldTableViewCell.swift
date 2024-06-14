//
//  SearchTextFieldTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import UIKit

class SearchTextFieldTableViewCell: UITableViewCell {

    @IBOutlet weak var searchBar: UISearchBar!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        searchBar.layer.cornerRadius = 8
        searchBar.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        searchBar.layer.borderWidth = 1
        searchBar.setSearchFieldBackgroundImage(UIImage(), for: .normal)
        searchBar.setBackgroundImage(UIImage(), for: .any, barMetrics: .default)
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
