﻿#pragma checksum "..\..\..\Dialogs\VoteDialog.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "486145C8ED37546D7BDCAEA85AEC5B86"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using ClassM8_Client.Dialogs;
using MaterialDesignThemes.Wpf;
using MaterialDesignThemes.Wpf.Transitions;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace ClassM8_Client.Dialogs {
    
    
    /// <summary>
    /// VoteDialog
    /// </summary>
    public partial class VoteDialog : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 31 "..\..\..\Dialogs\VoteDialog.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cbVoteCandidate;
        
        #line default
        #line hidden
        
        
        #line 33 "..\..\..\Dialogs\VoteDialog.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Label lblVoteError;
        
        #line default
        #line hidden
        
        
        #line 35 "..\..\..\Dialogs\VoteDialog.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Grid innerGrid;
        
        #line default
        #line hidden
        
        
        #line 40 "..\..\..\Dialogs\VoteDialog.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnVote;
        
        #line default
        #line hidden
        
        
        #line 41 "..\..\..\Dialogs\VoteDialog.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnCancle;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/ClassM8_Client;component/dialogs/votedialog.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\..\Dialogs\VoteDialog.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.cbVoteCandidate = ((System.Windows.Controls.ComboBox)(target));
            
            #line 31 "..\..\..\Dialogs\VoteDialog.xaml"
            this.cbVoteCandidate.Loaded += new System.Windows.RoutedEventHandler(this.ComboBox_Loaded);
            
            #line default
            #line hidden
            return;
            case 2:
            this.lblVoteError = ((System.Windows.Controls.Label)(target));
            return;
            case 3:
            this.innerGrid = ((System.Windows.Controls.Grid)(target));
            return;
            case 4:
            this.btnVote = ((System.Windows.Controls.Button)(target));
            
            #line 40 "..\..\..\Dialogs\VoteDialog.xaml"
            this.btnVote.Click += new System.Windows.RoutedEventHandler(this.btnVote_Click);
            
            #line default
            #line hidden
            return;
            case 5:
            this.btnCancle = ((System.Windows.Controls.Button)(target));
            
            #line 41 "..\..\..\Dialogs\VoteDialog.xaml"
            this.btnCancle.Click += new System.Windows.RoutedEventHandler(this.btnCancle_Click);
            
            #line default
            #line hidden
            return;
            }
            this._contentLoaded = true;
        }
    }
}
