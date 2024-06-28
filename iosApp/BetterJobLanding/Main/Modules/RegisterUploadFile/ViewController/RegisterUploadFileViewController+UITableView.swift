//
//  RegisterUploadFileViewController+UITableView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 26/06/2024.
//

import Foundation
import UIKit

extension RegisterUploadFileViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return formFields.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let registerFormFields = formFields[indexPath.row]
        switch registerFormFields.info.cellType{
        case .profileUpload:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: UploadProfileTableViewCell.self), for: indexPath) as? UploadProfileTableViewCell else {
                return UITableViewCell()
            }
            return cell
        case .inputTextfield:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: InputTextFieldTableViewCell.self), for: indexPath) as? InputTextFieldTableViewCell else {
                return UITableViewCell()
            }
          
            return cell
        default:
            return UITableViewCell()
        }
    }
}
