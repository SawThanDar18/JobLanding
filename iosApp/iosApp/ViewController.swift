//
//  ViewController.swift
//  iosApp
//
//  Created by Myint Zu on 03/06/2024.
//

import UIKit
import shared

class ViewController: UIViewController {

    @IBOutlet weak var greetingLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let greet = Greeting().greets()
        greetingLabel.text = greet
        // Do any additional setup after loading the view.
    }


}

