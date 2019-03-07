package dev.felicity.felicity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;
import com.google.firebase.auth.R.*;
import com.facebook.AccessToken;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.AuthCredential;



public class Login extends AppCompatActivity {


    private static final String TAG = "Facebook Login";
    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private android.widget.TextView mSignup;
    private FirebaseAuth mAuth, mFacebook;
    private FirebaseAuth.AuthStateListener mAuthListener;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        mEmail= findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginButton= findViewById(R.id.login);
        mAuth= FirebaseAuth.getInstance();
        mSignup= findViewById(R.id.signup);
        mFacebook= FirebaseAuth.getInstance();



        if(mFacebook.getCurrentUser() != null)
        {
            startActivity(new Intent(Login.this, LandingPage.class));
            finish();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth){
              if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                  startActivity(new Intent(Login.this, Demographics.class));
                  finish();
              }

            }
        };

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email= mEmail.getText().toString();
        String password= mPassword.getText().toString();

        if(android.text.TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(Login.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                    else if(!mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(Login.this,"You must verify email first",Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    public void goToSignUp(View v){
        startActivity(new Intent(Login.this, Signup.class));
    }



    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFacebook.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            popDialog();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }




    public void popDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Terms and Conditions");
        alertDialogBuilder.setMessage("1. GENERAL INFORMATION\n" +
                "1.1 FELICITY PRODUCTS\n" +
                "These Terms & Conditions (these “Terms”) contain the terms and conditions on which we supply content, products or services listed on www.felicitycbt.com (the “Website”), through our applications (the “Apps”) or via other delivery methods to you (the Website and such content, products, services and the Apps are collectively referred to herein as the “Product” or “Products”, which may be updated from time-to-time at the sole discretion of FELICITY). Please read these terms and conditions, carefully before ordering any Products from the Website or third party App stores (e.g. the Apple App Store, the Android Play Store, Amazon, etc.). The terms “FELICITY,” “us” or “we” refers to FELICITY. The term “Device” refers to the device which is used to access the Products including but not limited to computers, smart phones and tablets. The term “you” refers to the user of the Products. When you order (“Order”) any Products, or otherwise use or access the Products, you agree to be bound by these Terms and all applicable laws, rules and regulations. You may also be asked to click “I accept” at the appropriate place prior to your purchase of access to the Products. At such time, if you do not click “I accept”, you may not be able to complete such purchase or gain such access. By using the Products, you indicate that you accept these Terms and that you agree to abide by them. If you do not agree to these Terms, please refrain from using the Products.\n" +
                "\n" +
                "Our contact email address is help@FELICITY.com. All correspondence to FELICITY including any queries you may have regarding your use of the Products or these Terms should be sent to this contact email address.\n" +
                "\n" +
                "1.2 ARBITRATION NOTICE AND CLASS ACTION WAIVER\n" +
                "PLEASE NOTE THAT THESE TERMS CONTAIN AN ARBITRATION CLAUSE. EXCEPT FOR CERTAIN TYPES OF DISPUTES MENTIONED IN THE ARBITRATION CLAUSE, YOU AND FELICITY AGREE THAT DISPUTES RELATING TO THESE TERMS OR YOUR USE OF THE PRODUCTS WILL BE RESOLVED BY MANDATORY BINDING ARBITRATION, AND YOU AND FELICITY WAIVE ANY RIGHT TO PARTICIPATE IN A CLASS-ACTION LAWSUIT OR CLASS-WIDE ARBITRATION.\n" +
                "\n" +
                "1.3 BASIS OF LICENSE\n" +
                "(a) These Terms and the Order set out the whole agreement between you and us for the supply of the Products. In order to participate in certain Products, you may be required to agree to additional terms and conditions; those additional terms are hereby incorporated into these Terms. Where such terms are inconsistent with these Terms, the additional terms shall control.\n" +
                "\n" +
                "(b) Please check that the details in these Terms and on the Order are complete and accurate before you use or commit yourself to purchase the Products. If you think that there is a mistake, please make sure that you ask us to confirm any changes in writing, as we only accept responsibility for statements and representations made in writing by an officer of FELICITY.\n" +
                "\n" +
                "(c) AS PART OF YOUR USE OF THE PRODUCTS, YOU AFFIRMATIVELY CONSENT TO THE PROCESSING AND STORAGE OF YOUR PERSONAL INFORMATION IN THE UNITED STATES AND THE UNITED KINGDOM, INCLUDING THE PROCESSING AND STORING OF YOUR PERSONAL INFORMATION IN THE UNITED STATES AND THE UNITED KINGDOM FOR THE PURPOSES OF PROCESSING PAYMENTS AND TRACKING INDIVIDUAL USE OF THE PRODUCTS. BY USING THE PRODUCTS, YOU ACKNOWLEDGE THAT YOU UNDERSTAND AND AGREE THAT THE UNITED STATES AND THE UNITED KINGDOM MAY NOT HAVE THE SAME LEVEL OF PROTECTIONS FOR YOUR PERSONAL INFORMATION THAT EXIST IN YOUR COUNTRY OF RESIDENCE, AND YOU NONETHELESS CONSENT TO THE PROCESSING AND STORAGE OF YOUR PERSONAL INFORMATION IN THE UNITED STATES AND THE UNITED KINGDOM. WE WILL TAKE MEASURES AS REQUIRED TO COMPLY WITH APPLICABLE LAW REGARDING THE TRANSFER, STORAGE AND USE OF CERTAIN PERSONAL INFORMATION.\n" +
                "\n" +
                "1.4 CHANGES TO TERMS\n" +
                "FELICITY reserves the right to change or update these Terms, or any other of our policies or practices, at any time, and will notify users by posting such changed or updated Terms on this page. Any changes or updates will be effective immediately upon posting to www.felicitycbt.com. Your continued use of the Products constitutes your agreement to abide by the Terms as changed. Under certain circumstances we may also elect to notify you of changes or updates to our Terms by additional means, such as pop-up or push notifications within the Products or email.\n" +
                "\n" +
                "2. MEMBERSHIPS AND SUBSCRIPTIONS\n" +
                "2.1 BECOMING A MEMBER\n" +
                "(a) You may sign up as a registered user of the Products free of charge (a “Member”). To become a Member you need to go to the relevant section of the Products, then submit your email address to us, and create a username and password to be used in conjunction with that email address. You are responsible for maintaining the confidentiality of your account and password and for restricting access to your Device.\n" +
                "\n" +
                "(b) In the course of your use of the Products, you may be asked to provide certain personalized information to us (such information is referred to hereinafter as “User Information”). This User Information may include information from your Facebook and similar social networking profiles. Our information collection and use policies with respect to the privacy of such User Information are set forth in the FELICITY Privacy Policy. You acknowledge and agree that you are solely responsible for the accuracy and content of User Information, and you agree to keep it up to date.\n" +
                "\n" +
                "(c) By placing an Order through the Products, you warrant that:\n" +
                "\n" +
                "(i) You are legally capable of entering into binding contracts; (ii) All registration information you submit is truthful and accurate; (iii) You will maintain the accuracy of such information; and (iv) Your use of the Products does not violate any applicable law or regulation.\n" +
                "\n" +
                "2.2 ONCE A MEMBER\n" +
                "You are responsible for maintaining the confidentiality of your account, password and other User Information and for restricting access to your Device to further help protect such information. You are responsible for updating your User Information.\n" +
                "\n" +
                "2.3 USE OF FELICITY BY MINORS\n" +
                "You must be 18 years of age, or the age of majority in your province, territory or country, to sign up as a registered user of the Products. Individuals under the age of 18, or the applicable age of majority, may utilize the Products only with the involvement and consent of a parent or legal guardian, under such person\\'s account and otherwise subject to these Terms.\n" +
                "\n" +
                "2.4 MEMBERSHIP\n" +
                "As a FELICITY Member you will receive access to certain sections, features and functions of the Products that are not available to non-members.\n" +
                "\n" +
                "By agreeing to become a Member you opt-in to receiving occasional special offer, marketing, survey and Product based communication emails. You can easily unsubscribe from FELICITY commercial emails by following the opt-out instruction in these emails. FELICITY memberships and subscriptions are not transferable and therefore cannot be sold or exchanged or transferred in any way whatsoever.\n" +
                "\n" +
                "2.5 SUBSCRIPTIONS\n" +
                "(a) FELICITY account holders may access the Products in two ways:\n" +
                "\n" +
                "(i) \"Basics\" Free Trial: a free-of-charge program, which gives unlimited access to ten days of our “Foundation Course.”\n" +
                "(ii) Paid Subscription: a subscription fee-based program, which gives access to all content including and beyond the \"Basics\" Free Trial. You will only have access to the Subscription Program while your subscription is active and subsisting. You may have access to a free trial period of the Subscription Program in accordance with certain promotional offers. All subscription services provide access through the Products. You can become a subscriber to the Subscription Program by purchasing a subscription to the Products from the Website, within the Apps, where allowed by the App marketplace partners, or through a bundle with one or more of our bundle subscription partners. Please note that if you purchase a subscription through the Apple iTunes Store or our iPhone application, the sale is final, and we will not provide a refund. Your purchase will be subject to Apple’s applicable payment policy, which also may not provide for refunds. If you purchase a subscription through the Google Play store, the sale is final and we will not provide a refund. Your purchase will be subject to Google’s applicable payment policy, which also may not provide for refunds. If you purchase through one or more of our bundle subscription partners, the purchase may be further subject to the Terms and Conditions of such partners, and payment and management of the bundle subscription may be administered by them.\n" +
                "(b) FELICITY offers monthly, annual, two year and forever subscription options. For the purposes of our monthly and yearly subscriptions, a month constitutes 30 calendar days, a year constitutes 365 calendar days and two years constitutes 730 calendar days. For the purposes of our forever subscription, forever constitutes 100 years or until the date FELICITY ceases to commercially offer the Products.\n" +
                "\n" +
                "(c) Our “Monthly” subscription is paid in monthly installments. For each month that your monthly subscription is active, you acknowledge and agree that FELICITY is authorized to charge the same credit card as was used for the initial subscription fee or other payment method as set forth in section 2.5(h) (the “Payment Method”) in the amount of the current monthly subscription fee as of the time of renewal. The monthly renewal subscription fees will continue to be billed to the Payment Method you provided, automatically until cancelled. You must cancel your subscription before it renews each month in order to avoid billing of the next month’s subscription fee to the Payment Method you provided. Refunds cannot be claimed for any partial-month subscription period.\n" +
                "\n" +
                "(d) Our “Yearly” and “Two Year” subscriptions are paid for by an upfront one-off payment with automatic annual or two year renewals respectively. You acknowledge and agree that FELICITY is authorized to charge the Payment Method used for (i) the initial annual or two year subscription fee at the rate secured at the time of purchase, and (ii) the renewal subscription fee(s) at the non-discounted rate in effect at the time of any such renewal. You must cancel your subscription before it renews in order to avoid billing of the renewal subscription fee to the Payment Method you provided. Refunds cannot be claimed for any partial subscription period.\n" +
                "\n" +
                "(e) Our “Forever” subscription is paid for by a one-off upfront payment.\n" +
                "\n" +
                "(f) FELICITY offers certain special discount pricing options (the “Special Discount Pricing Options”). The Special Discount Pricing Options will permit users to access to the same content included in the Paid Subscription; such Special Discount Pricing Options shall only be available to qualified users (the “Qualified Users”). To be considered a Qualified User, your information will be provided directly FELICITY’s third-party verification system. FELICITY reserves the right to determine if you are a Qualifying User in our sole discretion. FELICITY Special Discount Pricing Options include the following:\n" +
                "\n" +
                "1. The Student Discount Offer, the terms of which can be found in the Student Discount Offer Terms and Conditions.\n" +
                "(g) You agree to promptly notify FELICITY of any changes to the Payment Method you provided while any subscriptions remain outstanding. You are responsible for all applicable fees and charges incurred, including applicable taxes, and all subscriptions purchased by you.\n" +
                "\n" +
                "(h) In the course of your use of the Products, FELICITY and its third party payment service provider may receive and implement updated credit card information from your credit card issuer in order to prevent your subscription from being interrupted by an outdated or invalid card. This disbursement of the updated credit card information is provided to FELICITY and FELICITY’s third party payment service provider at the sole election of your credit card issuer. Your credit card issuer may give you the right to opt-out of the update service. Should you desire to do so, please contact your credit card issuer.\n" +
                "\n" +
                "(i) Our obligation to provide the Products only comes into being when we take receipt of your Order, and we confirm your purchase to you by email. We shall confirm your Order and send you an email to confirm your access to the subscription purchased. Please quote the Order number in all subsequent correspondence with us. Prices in US Dollars and Euros include local taxes. All prices in Pound Sterling include VAT unless otherwise stated. You agree not to hold us responsible for banking charges incurred due to payments on your account. If payment is not received by us from the Payment Method you provided, you agree to pay all amounts due upon demand by us. You agree that you are not permitted to resell any Products purchased through FELICITY for commercial purposes.\n" +
                "\n" +
                "2.6 DEVICE REQUIREMENTS\n" +
                "To enjoy FELICITY via your smartphone or other Device, your Device must satisfy certain system requirements. These requirements can be found on the Website and the Google, Apple and Amazon App marketplaces.\n" +
                "\n" +
                "2.7 GIFTING\n" +
                "“Gift Subscriptions” are pre-paid memberships to the Products. A person who purchases the gift is referred to in these terms as the “Giftor”. A person who receives and redeems a Gift Subscription to the Products is referred to in these terms as the “Recipient”. Gift subscriptions are paid for as a one-off upfront payment. Once bought, the Giftor will receive an Order confirmation and receipt. The FELICITY gift subscription will be sent to the Recipient on the Giftor’s specified date. Gifting codes can only be used once in the country for which they were purchased and cannot be redeemed for cash, resold or combined with any other offers, including free trial. Please note that gifting codes cannot be redeemed if the Recipient has already purchased a subscription through the Apple iTunes Store or our iPhone application, or the Google Play Store or our Android application. We will automatically bill the Payment Method you provided for any purchased Gift Subscriptions at the time of purchase, not delivery. There are no refunds or other credits for Gift Subscription that are not redeemed. FELICITY will notify the Recipient prior to the end of the Gift Subscription that the gift period is about to expire. FELICITY is not responsible if a Gift Subscription is lost, stolen or used without permission.\n" +
                "\n" +
                "2.8 CORPORATE AND OTHER CONSUMER COMMUNITIES\n" +
                "While FELICITY is a consumer products company, there is increasing interest by large consumer communities (corporations, universities, hospitals, etc.) (“Communities”) to introduce the Products to their employees and members. In some cases, these Communities may supplement these Terms with their own terms and conditions. In such event, these Community terms and conditions shall also apply to your use of the Products. In the event of any conflict with such additional terms and these Terms, these Terms shall prevail.\n" +
                "\n" +
                "2.9 CHANGING FEES AND CHARGES\n" +
                "We reserve the right to change our subscription plans or adjust pricing for our service or any components thereof in any manner and at any time as we may determine in our sole and absolute discretion. Except as otherwise expressly provided for in these Terms, any price changes or changes to your subscription plan will take effect following notice to you.\n" +
                "\n" +
                "3. CANCELLATION OF SERVICES\n" +
                "3.1 CANCELLATION BY YOU\n" +
                "(a) You may cancel a Monthly subscription at any time. Cancellation is effective at the end of the applicable monthly period. Please make any such cancellation by visiting here or emailing help@FELICITY.com.\n" +
                "\n" +
                "(b) You may cancel our Yearly, Two Year and Forever subscription plans within the 30-day money back guarantee offer, which entitles you to cancel your subscription and have the full cost refunded to you up to 30 calendar days from your first date of payment, by emailing help@FELICITY.com. You are entitled to one refund only. After your refund, any future subscriptions will no longer qualify for the 30-day money back guarantee. No such refunds will apply to subsequent renewals of the Yearly and Two Year subscriptions or subscriptions purchased through the Apple iTunes Store or our iPhone application, or the Google Play Store or our Android application.\n" +
                "\n" +
                "(c) Please note that if you purchase a subscription through the Apple iTunes Store or our iPhone application, you may cancel your subscription by cancelling automatic renewal of paid In App Subscriptions by selecting Manage App Subscriptions in your iTunes Account settings and selecting the subscription you want to modify. If you purchase a subscription through the Google Play store you may cancel automatic renewals in account settings under Subscriptions in the Google Play app, or according to the current process outlined by Google Play.\n" +
                "\n" +
                "3.2 CANCELLATION BY US\n" +
                "We may suspend or terminate your use of the Products as a result of your fraud or breach of any obligation under these Terms. Such termination or suspension may be immediate and without notice. A breach of these Terms, includes without limitation, the unauthorized copying or download of our audio or video content from the Products.\n" +
                "\n" +
                "3.3 PROMOTION AND DISCOUNT CODES\n" +
                "Any promotion code or offer (including the Special Discount Pricing Options) provided by us may not be used in conjunction with any other promotion code or offer, past or present. Introductory offers are only available to new users of the Products, except where expressly stated otherwise. Previous users or trial users of the Products do not qualify as new users. No promotion code or discount will apply to corporate or other Community subscriptions. Unless otherwise set forth in the terms of any promotion, all pricing promotions or discounts will apply to the initial period of the subscription, and any renewals will be charged at the rate in effect at the time of renewal for the type of subscription purchased.\n" +
                "\n" +
                "4. PROHIBITED USE OF THE PRODUCTS\n" +
                "4.1 You agree not to upload, post, email or otherwise send or transmit or introduce any material that contains software viruses or any other computer code, files or programs designed to interrupt, harm, damage, destroy or limit the functionality of any computer software or hardware or equipment linked directly or indirectly with the Products or the Products themselves. You agree not to interfere with the servers or networks underlying or connected to the Products or to violate any of the procedures, policies or regulations of networks connected to the Products. You may not access the Products in an unauthorized manner.\n" +
                "\n" +
                "4.2 You agree not to impersonate any other person while using the Products, conduct yourself in an offensive manner while using the Products, or use the Products for any illegal, immoral or harmful purpose.\n" +
                "\n" +
                "4.3 By breaching the provisions of this section 4, you may commit a criminal offense under applicable laws. We may report any such breach to the relevant law enforcement authorities and we may cooperate with those authorities by disclosing your identity to them. In the event of such a breach, your right to use the Products will cease immediately.\n" +
                "\n" +
                "4.4 You agree not to use the Products for any purposes related to scientific research, analysis or evaluation of the Products without the express written consent of FELICITY.\n" +
                "\n" +
                "5. MATERIALS OFFERED THROUGH THE PRODUCTS\n" +
                "5.1 COPYRIGHT\n" +
                "(a) All materials (including software and content whether downloaded or not) contained in the Products are owned by FELICITY (or our affiliates and/or third party licensors, where applicable), unless indicated otherwise. You agree and acknowledge that the materials are valuable property and that other than any specific and limited license for use of such materials, you shall not acquire any ownership rights in or to such materials. The materials may not be used except as provided for in these Terms, and any other relevant terms and conditions provided to you without our prior written permission.\n" +
                "\n" +
                "(b) You acknowledge and agree that certain materials on or in the Products are the property of third party licensors and, without prejudice to any and all other rights and remedies available, each such licensor has the right to directly enforce relevant provisions of section 12 against you.\n" +
                "\n" +
                "(c) Audio or video content from FELICITY not explicitly indicated as downloadable may not be downloaded or copied from the Products or any Device.\n" +
                "\n" +
                "(d) The Products are not intended for your commercial use. Commercial advertisements, affiliate links, and other forms of solicitation may be removed by us without notice and may result in termination of privileges. You must not use any part of the materials used in or on the Products for commercial purposes without obtaining a written license to do so from us. Material from the Products may not be copied or distributed, or republished, or transmitted in any way, without our prior written consent. Any unauthorized use or violation of these Terms immediately and automatically terminates your right to use the Products and may subject you to legal liability. You agree not to use the Products for illegal purposes (including, without limitation, unlawful, harassing, libelous, invasion of another’s privacy, abusive, threatening or obscene purposes) and you agree that you will comply with all laws, rules and regulations related to your use of the Products. Appropriate legal action may be taken for any illegal or unauthorized use of the Products.\n" +
                "\n" +
                "(e) A limited amount of content may be marked and authorized for the user to share in their personal social channels (Facebook, Twitter, etc.). With respect to content made available by FELICITY through the Products that is specifically identified as available for distribution by you (“Distribution Content”) as part of your blog or other online commentary, analysis or review (“User Commentary”), FELICITY grants you a limited right to download, reproduce and distribute Distribution Content over the internet as part of your User Commentary. You may also modify such Distribution Content but only as required to technically enable the display and distribution of such content through your computer systems and over the Internet (e.g. a change in video format or file size) provided such modification does not materially alter the substance or quality of such content. Your display and distribution of Distribution Content may also be subject to other terms and conditions that are set forth in the description of such content in the Products, such as display and distribution of Distribution Content only within specified usage dates. You agree not to publish the Distribution Content with other content that is known by you to be false, inaccurate, or misleading or that is, or that encourages activity or conduct that is, unlawful, harmful, threatening, abusive, harassing, tortious, defamatory, vulgar, obscene, pornographic, libelous, invasive of another’s privacy, hateful, or racially, ethnically or otherwise objectionable. Distribution Content may contain trackers that enable us to collect information with respect to the distribution and consumption of such content.\n" +
                "\n" +
                "(f) You may not otherwise download, display, copy, reproduce, distribute, modify, perform, transfer, create derivative works from, sell or otherwise exploit any content, code, data or materials in the Products. If you make other use of the Products, or the content, code, data or materials thereon, except as otherwise provided above, you may violate copyright and other laws of the United States, other countries, as well as applicable state laws and may be subject to liability for such unauthorized use. FELICITY will enforce its intellectual property rights to the fullest extent of the law, including the seeking of criminal prosecution.\n" +
                "\n" +
                "5.2 TRADEMARKS\n" +
                "FELICITY®, the FELICITY logo and all other FELICITY product or service marks are trademarks of FELICITY. All intellectual property, other trademarks, logos, images, product and company names displayed or referred to on or in the Products are the property of their respective owners. Nothing grants you any license or right to use, alter or remove or copy such material. Your misuse of the trademarks displayed on the Products is strictly prohibited. FELICITY will enforce its trademark rights to the fullest extent of the law, including the seeking of criminal prosecution.\n" +
                "\n" +
                "6. AVAILABILITY OF PRODUCTS\n" +
                "6.1 Although we aim to offer you the best service possible, we make no promise that the Products will meet your requirements and we cannot guarantee that the Products will be fault free. If a fault occurs in the Products, please report it to us at help@FELICITY.com and we will review your complaint and, where we determine it is appropriate to do so, correct the fault. If the need arises, we may suspend access to the Products while we address the fault. We will not be liable to you if the Products are unavailable for a commercially reasonable period of time.\n" +
                "\n" +
                "6.2 Your access to the Products may be occasionally restricted to allow for repairs, maintenance or the introduction of new facilities or Products. We will restore the Products as soon as we reasonably can. In the event that the Products are unavailable, our usual Order and cancellation deadlines apply; please notify us of changes to your Order by emailing help@FELICITY.com.\n" +
                "\n" +
                "7. USER MATERIAL\n" +
                "7.1 The Products may let you submit material to us: for example, you may be able to upload a photo to your profile, post subjects and comments in the community and comment on various matters in various parts of the Products. You may be able to upload video, images or sounds. In these Terms, we use the term “User Material” to refer to any publically available material of any kind that you submit to us, including text, files, images, photos, video, sounds and musical or literary works. User Material does not include the account information, Product purchase, or Product use information which you provide in registering for and using Products.\n" +
                "\n" +
                "7.2 This section 7 sets out the rights and obligations that each of us have in connection with User Material. If you review or submit User Material, you are agreeing to do so in accordance with these Terms. If you do not want to review or submit User Material in accordance with these Terms, then you should not do so.\n" +
                "\n" +
                "7.3 We do not systematically review User Material submitted by you or other users. We are not responsible for the content of User Material provided by you or any other user. We do not necessarily endorse any opinion contained in such material. We make no warranties or representations, express or implied, about User Material, including as to its legality or accuracy.\n" +
                "\n" +
                "7.4 We reserve the right, in our sole discretion, to refuse to post or to remove or edit any of your User Material, or to restrict, suspend, or terminate your access to all or any part of the Products, particularly where User Material breaches this section 7, and we may do this with or without giving you any prior notice.\n" +
                "\n" +
                "7.5 We may link User Material or parts of User Material to other material, including material submitted by other users or created by FELICITY or other third parties. We may use User Material for our internal business purposes, for example, to examine trends or categories or to promote, market or advertise FELICITY. You acknowledge that we may indirectly commercially benefit from use of your User Material.\n" +
                "\n" +
                "7.6 Each time you submit User Material to us, you represent and warrant to us as follows:\n" +
                "\n" +
                "(a) You own your User Material or have the right to submit it, and in submitting it you will not be infringing any rights of any third party, including intellectual property rights (such as copyright or trade mark), privacy or publicity rights, rights of confidentiality or rights under contract.\n" +
                "(b) Your User Material is not illegal, obscene, defamatory, threatening, pornographic, harassing, hateful, racially or ethnically offensive, and does not encourage conduct that would be considered a criminal offense, and does not give rise to civil liability, violate any law, or is otherwise deemed inappropriate.\n" +
                "(c) Your User Material does not advertise any product or service or solicit any business.\n" +
                "(d) Your User Material does not identify any individual (including by way or name, address or a still picture or video) under the age of 18 and if User Material identifies any individual over the age of 18, you have that person’s consent to being identified in exactly that way in your User Material; and in submitting your User Material you are not impersonating any other person.\n" +
                "(e) You will not collect usernames and/or email addresses of users for the purpose of sending unsolicited email.\n" +
                "(f) You will not engage in criminal or tortious activity, including fraud, spamming, spimming, sending of viruses or other harmful files, copyright infringement, patent infringement, or theft of trade secrets or attempt to impersonate another user or person.\n" +
                "(g) You will not engage in any automated use of the system, such as using scripts to alter our content.\n" +
                "(h) You will not, without authorization, access, tamper with, or use non-public areas of the Products, FELICITY’s computer systems, or the technical delivery systems of FELICITY’s providers.\n" +
                "(i) Except as necessary to maintain your own computer security by use of commercial-off-the-shelf anti-virus or anti-malware products, you will not attempt to probe, scan, or test the vulnerability of the Products or any other FELICITY system or network or breach any security or authentication measures.\n" +
                "7.7 We are entitled to identify you to third parties who claim that their rights have been infringed by User Material you have submitted.\n" +
                "\n" +
                "7.8 User Material is not considered to be confidential. You agree not to submit any content as User Material in which you have any expectation of privacy. We do not claim any ownership rights in User Material. However, by submitting User Material you hereby grant FELICITY an irrevocable, perpetual, non-exclusive, royalty free, worldwide license to use, telecast, copy, perform, display, edit, distribute and otherwise exploit the User Material you post on the Products, or any portion thereof, and any ideas, concepts, or know how contained therein, with or without attribution, and without the requirement of any permission from or payment to you or to any other person or entity, in any manner (including, without limitation, for commercial, publicity, trade, promotional, or advertising purposes) and in any and all media now known or hereafter devised, and to prepare derivative works of, or incorporate into other works, such User Material, and to grant and authorize sublicenses of the foregoing without any payment of money or any other form of consideration to you or to any third party. FELICITY may include your User Material in FELICITY’s Distribution Content that is made available to others through the Products. Be aware that FELICITY has no control over User Material once it leaves the Products, and it is possible that others may duplicate material found on the Products, including, but not limited to, on other sites on the Internet. You represent and warrant that you own or otherwise control the rights to your User Material. You agree to indemnify FELICITY and its affiliates for all claims arising from or in connection with any claims to any rights in your User Material or any damages arising from your User Material.\n" +
                "\n" +
                "7.9 Any inquiries, feedback, suggestions, ideas, other information which is not part of your use of the Products or User Material that you provide to us (collectively, “Submissions”) will be treated as non-proprietary and non-confidential. By transmitting, uploading, posting, e-mailing, or otherwise submitting Submissions to the Products, you grant, and you represent and warrant that you have the right to grant, to FELICITY an irrevocable, perpetual, non-exclusive, royalty free, worldwide license to use, telecast, copy, perform, display, edit, distribute and otherwise exploit the Submissions, or any portion thereof and any ideas, concepts, or know how contained therein, with or without attribution, and without the requirement of any permission from or payment to you or to any other person or entity, in any manner (including, without limitation, for commercial, publicity, trade, promotional, or advertising purposes) and in any and all media now known or hereafter devised, and to prepare derivative works of, or incorporate into other works, such Submissions, and to grant and authorize sublicenses of the foregoing without any payment of money or any other form of consideration to you or to any third party. You also acknowledge that your Submissions will not be returned to you and that FELICITY has no obligation to acknowledge receipt of or respond to any Submissions. If you make a Submission, you represent and warrant that you own or otherwise control the rights to your Submission. You agree to indemnify FELICITY and its affiliates for all claims arising from or in connection with any claims to any rights in any Submission or any damages arising from any Submission.\n" +
                "\n" +
                "8. LINKS TO WEBSITES/HOME PAGE\n" +
                "8.1 We may provide links to other websites or services for you to access. You acknowledge that any access is at your sole discretion and for your information only. We do not review or endorse any of those websites or services. We are not responsible in any way for:(a) the availability of, (b) the privacy practices of, (c) the content, advertising, products, goods or other materials or resources on or available from, or (d) the use to which others make of these other websites or services. We are also not responsible for any damage, loss or offense caused or alleged to be caused by, or in connection with, the use of or reliance on such websites or services.\n" +
                "\n" +
                "8.2 You may link to our home page, provided you do so in a way that is fair and legal and does not damage our reputation or take advantage of it, but you must not establish a link in such a way as to suggest any form of association, approval or endorsement on our part where none exists. You must not establish a link from any website that is not owned by you. The Products must not be framed on any other website, nor may you create a link to any part of the Products unless you have written permission to do so from FELICITY. We reserve the right to withdraw linking permission with written notice. The website from which you are linking must comply in all respects with the content standards set out in our acceptable use policy. If you wish to make any use of material on or in the Products other than that set out above, please address your request to help@FELICITY.com.\n" +
                "\n" +
                "9. PRODUCTS DISCLAIMER\n" +
                "The information contained in the Products is for general information purposes only. While we endeavor to keep the information up-to-date and correct, we make no representations or warranties of any kind, express or implied, about the completeness, accuracy, reliability, suitability or availability with respect to the Products or the information contained on the Products for any purpose. Any reliance you place on such information is therefore strictly at your own risk.\n" +
                "\n" +
                "10. MEDICAL DISCLAIMER\n" +
                "10.1 FELICITY is a provider of online and mobile meditation content in the health & wellness space. We are not a health care or medical device provider, nor should our Products be considered medical advice. Only your physician or other health care provider can do that. While there is third party evidence from research that meditation can assist in the prevention and recovery process for a wide array of conditions as well as in improving some performance and relationship issues, FELICITY makes no claims, representations or guarantees that the Products provide a therapeutic benefit.\n" +
                "\n" +
                "10.2 Any health information and links on the Products, whether provided by FELICITY or by contract from outside providers, is provided simply for your convenience.\n" +
                "\n" +
                "10.3 Any advice or other materials in the Products are intended for general information purposes only. They are not intended to be relied upon and are not a substitute for professional medical advice based on your individual condition and circumstances. The advice and other materials we make available are intended to support the relationship between you and your healthcare providers and not replace it. We are not liable or responsible for any consequences of your having read or been told about such advice or other materials as you assume full responsibility for your decisions and actions. In particular, to the fullest extent permitted by law, we make no representation or warranties about the accuracy, completeness, or suitability for any purpose of the advice, other materials and information published as part of the Products.\n" +
                "\n" +
                "10.4 There have been rare reports where people with certain psychiatric problems like anxiety and depression have experienced worsening conditions in conjunction with intensive meditation practice. People with existing mental health conditions should speak with their health care providers before starting a meditation practice.\n" +
                "\n" +
                "11. END USER LICENSE\n" +
                "11.1 Subject to the terms of this license agreement (“License Agreement”), as set out in this section 11, and these other Terms, and your payment of applicable subscription fees, FELICITY grants you a limited, non-exclusive, revocable license to stream, download and make personal non-commercial use of the Products.\n" +
                "\n" +
                "11.2 The Products contain or embody copyrighted material, proprietary material or other intellectual property of FELICITY or its licensors. All right, title and ownership in the Products remain with FELICITY or its licensors, as applicable. The rights to download and use the Products are licensed to you and are not being sold to you, and you have no rights in them other than to use them in accordance with this License Agreement and our other Terms.\n" +
                "\n" +
                "11.3 You agree that you will not and you will not assist or permit any third party to:\n" +
                "\n" +
                "(a) Copy, store, reproduce, transmit, modify, alter, reverse-engineer, emulate, de-compile, or disassemble the Products in any way, or create derivative works of the Products;\n" +
                "(b) Use the Products or any part of them to create any tool or software product that can be used to create software applications of any nature whatsoever;\n" +
                "(c) Rent, lease, loan, make available to the public, sell or distribute the Products in whole or in part;\n" +
                "(d) Tamper with the Products or circumvent any technology used by FELICITY or its licensors to protect any content accessible through the Products;\n" +
                "(e) Circumvent any territorial restrictions applied to the Products; or\n" +
                "(f) Use the Products in a way that violates this License Agreement or the other Terms.\n" +
                "11.4 You may not make the Products available to the public. The Products made available (in whole or in part) are owned by FELICITY or its licensors and your use of them must be in accordance with these Terms.\n" +
                "\n" +
                "12. DIGITAL MILLENIUM COPYRIGHT ACT (“DMCA”) NOTICE\n" +
                "12.1 We are committed to complying with copyright and related laws, and we require all users of the Products to comply with these laws. Accordingly, you may not store any material or content on, or disseminate any material or content over, the Products in any manner that constitutes an infringement of third party intellectual property rights, including rights granted by copyright law. Owners of copyrighted works in the United States who believe that their rights under copyright law have been infringed may take advantage of certain provisions of the US Digital Millennium Copyright Act of 1998 (the “DMCA”) to report alleged infringements. You may not post, modify, distribute, or reproduce in any way any copyrighted material, trademarks, or other proprietary information belonging to others without obtaining the prior written consent of the owner of such proprietary rights. It is our policy to terminate privileges of any user who repeatedly infringes the copyright rights of others upon receipt of proper notification to us by the copyright owner or the copyright owner’s legal agent.\n" +
                "\n" +
                "12.2 If you feel that a posted message is objectionable or infringing, we encourage you to contact us immediately. Upon our receipt of a proper notice of claimed infringement under the DMCA, we will respond expeditiously to remove, or disable access to, the material claimed to be infringing and will follow the procedures specified in the DMCA to resolve the claim between the notifying party and the alleged infringer who provided the content in issue. Our designated agent (i.e., the proper party) to whom you should address such notice is listed below.\n" +
                "\n" +
                "12.3 If you believe that your work has been copied and posted on the Products in a way that constitutes copyright infringement, please provide our designated agent with the following information:\n" +
                "\n" +
                "(a) An electronic or physical signature of the person authorized to act on behalf of the owner of the copyright or other intellectual property interest;\n" +
                "(b) A description of the copyrighted work or other intellectual property that you claim has been infringed;\n" +
                "(c) A description of where the material that you claim is infringing is located on the Products;\n" +
                "(d) Your address, telephone number, and email address;\n" +
                "(e) A statement by you that you have a good faith belief that the disputed use is not authorized by the copyright or intellectual property owner, its agent, or the law; and\n" +
                "(f) A statement by you, made under penalty of perjury, that the information contained in your report is accurate and that you are the copyright or intellectual property owner or authorized to act on the copyright or intellectual property owner’s behalf.\n" +
                "(g) Our designated agent for notice of claims of copyright infringement can be reached as follows:\n" +
                "By Mail: FELICITY, Inc. Attn: Copyright Agent 500 Molino St., Suite 118 Los Angeles, CA 90013\n" +
                "\n" +
                "By E-Mail: hcannom@wscylaw.com Subject line: DMCA\n" +
                "\n" +
                "13. GENERAL TERMS AND CONDITIONS\n" +
                "13.1 ASSIGNMENT BY US\n" +
                "FELICITY may transfer its rights and obligations under these Terms to any company, firm or person at any time if it does not materially affect your rights under it. You may not transfer your rights or obligations under these Terms to anyone else. These Terms are personal to you and no third party is entitled to benefit under these Terms except as set out here.\n" +
                "\n" +
                "13.2 INDEMNITY BY YOU\n" +
                "You agree to defend, indemnify and hold FELICITY and its directors, officers, members, investors, managers, employees and agents harmless from any and all claims, liabilities, costs and expenses, including reasonable attorneys’ fees, arising in any way from your use of the Products, your placement or transmission of any message, content, information, software, or other submissions through the Products, or your breach or violation of the law or of these Terms. FELICITY reserves the right, at its own expense, to assume the exclusive defense and control of any matter otherwise subject to indemnification by you, and in such case, you agree to cooperate with FELICITY defense of such claim.\n" +
                "\n" +
                "13.3 WARRANTIES AND LIMITATIONS\n" +
                "(a) We warrant to you that any Product purchased from us will, on delivery, conform in all material respects with its description and be of reasonably satisfactory quality.\n" +
                "\n" +
                "(b) We warrant that we will use reasonable skill and care in making the Products available to you during your subscription.\n" +
                "\n" +
                "(c) Nothing in this sections 13.3 or otherwise in these Terms shall exclude or in any way limit FELICITY’s liability for: fraud; death or personal injury caused by negligence; or liability to the extent the same may not be excluded or limited as a matter of law.\n" +
                "\n" +
                "(d) The Products and their content are otherwise provided on an “as is” basis and we make no representations or warranties of any kind with respect to them, including as to the accuracy, completeness or currency of the Products or their content. We assume no liability or responsibility for any errors or omissions in the content of the Products, or any failures, delays, or interruptions in the provision of the Products. We disclaim and exclude any express or implied warranties or representations, including any warranties as to merchantability or fitness for a particular purpose of the Products to the broadest extent permitted by law. We make no warranties or representations, express or implied, as to the timeliness, accuracy, quality, completeness or existence of the content and information posted on the Products. We make no warranties or representations, express or implied, for technical accessibility, fitness or flawlessness of the Products. We make no warranties or representations that your use of content and information posted on the Products will not infringe rights of third parties.\n" +
                "\n" +
                "(e) All conditions, warranties and other terms which might otherwise be implied by statute, common law or the law of equity are, to the extent permitted by law, excluded.\n" +
                "\n" +
                "13.4 NO WAIVER\n" +
                "If we delay exercising or fail to exercise or enforce any right available to us under these Terms, such delay or failure does not constitute a waiver of that right or any other rights under these Terms.\n" +
                "\n" +
                "13.5 FORCE MAJEURE\n" +
                "We will not be liable to you for any lack of performance, or the unavailability or failure, of the Products, or for any failure or delay by us to comply with these Terms, where such lack, unavailability or failure arises from any cause beyond our reasonable control.\n" +
                "\n" +
                "13.6 INTERPRETATION\n" +
                "In these Terms, unless the context requires otherwise: i) any phrase introduced by the words “including”, “include”, “in particular”, “for example” or any similar expression shall be construed as illustrative only and shall not be construed as limiting the generality of any preceding words; and ii) references to the singular include the plural and to the masculine include the feminine, and in each case vice versa.\n" +
                "\n" +
                "13.7 ELECTRONIC COMMUNICATIONS\n" +
                "(a) Applicable laws require that some of the information or communications we send to you should be in writing. When using the Products, you agree to transact with us electronically, and that communication with us will be mainly electronic. We will contact you by e-mail or provide you with information by posting notices on the Products. You agree to this electronic means of communication and you acknowledge that all contracts, notices, information and other communications that we provide to you electronically comply with any legal requirement that such communications be in writing.\n" +
                "\n" +
                "(b) In order to retain a copy, please select “Print,” and select the appropriate printer. If you do not have a printer, you can copy the text and the underlying agreement(s) and paste them into a new document in a word processor or a text editor on your computer and save the text.\n" +
                "\n" +
                "(c) You have the right to receive a paper copy of the communications. To receive a paper copy, please request it by emailing us at help@FELICITY.com\n" +
                "\n" +
                "(d) We may charge you a reasonable service charge to mail you a paper copy of any communication. We will either include such service charge on our fee schedule or we will first inform you of the charge and provide you with the choice as to whether you still want us to send you a paper copy. Please be sure to state that you are requesting a copy of the particular communication.\n" +
                "\n" +
                "(e) To receive and view an electronic copy of the communications you must have the following equipment and software:\n" +
                "\n" +
                "(i) A personal computer or other device which is capable of accessing the Internet. Your access to this page verifies that your system/device meets these requirements.\n" +
                "(ii) an Internet web browser which is capable of supporting 128-bit SSL encrypted communications, JavaScript, and cookies. Your system or device must have 128-bit SSL encryption software. Your access to this page verifies that your browser and encryption software/device meet these requirements.\n" +
                "(f) To retain a copy, you must either have a printer connected to your personal computer or other device or, alternatively, the ability to save a copy through use of printing service or software such as Adobe Acrobat®. If you have a word processor or text editor program on your computer, then you can also copy the text and paste the text into a new document in the word processor or text editor and save the text.\n" +
                "\n" +
                "(g) You can also contact us via email at help@FELICITY.com to withdraw your consent to receive any future communications electronically, including if the system requirements described above change and you no longer possess the required system. If you withdraw your consent, we may terminate your use of the Products.\n" +
                "\n" +
                "(h) We reserve the right, in our sole discretion, to discontinue the provision of your electronic communications, or to terminate or change the terms and conditions on which we provide electronic communications. We will provide you with notice of any such termination or change as required by law.\n" +
                "\n" +
                "13.8 NOTICES\n" +
                "Unless otherwise specifically indicated, all notices given by you to us must be given to FELICITY at help@FELICITY.com. We may give notice to you at the e-mail address you provide to us when you register, or in any of the ways specified in section 13.7 above. Notice will be deemed received and properly served immediately when posted on the Products or when an e-mail or other electronic communication is sent. In proving the service of any notice via email, it will be sufficient to prove that such e-mail was sent to the specified e-mail address of the addressee.\n" +
                "\n" +
                "13.9 ENTIRE AGREEMENT\n" +
                "These Terms and any document expressly referred to in them constitute the whole agreement between us and supersede all previous discussions, correspondence, negotiations, previous arrangement, understanding or agreement between us relating to their subject matter. We each acknowledge that neither of us relies on, or will have any remedies in respect of, any representation or warranty (whether made innocently or negligently) that is not set out in these Terms or the documents referred to in them. Each of us agrees that our only liability in respect of those representations and warranties that are set out in this agreement (whether made innocently or negligently) will be for breach of contract. Nothing in this section limits or excludes any liability for fraud.\n" +
                "\n" +
                "13.10 THIRD PARTY RIGHTS\n" +
                "A person who is not party to these Terms will not, subject to section 12 (DMCA), have any rights under or in connection with these Terms.\n" +
                "\n" +
                "13.11 OUR LIABILITY\n" +
                "(a) We will use reasonable endeavors to remedy faults in the Products. If we fail to comply with these Terms, we will be liable to you only for the purchase price of the Products in question. In addition, we will not be liable for:\n" +
                "\n" +
                "(i) Faulty operation of computers during the registration process or during completion of a subscription or during the transmission of any data and/or for incorrect or overly slow transmission of data by the internet provider and/or any damage that occurs due to information submitted by you not being received by us or not being received promptly or not being considered, as a consequence of technical faults with our software or hardware (whether or not they are within or outside of our control).\n" +
                "(ii) Any loss or damage due to viruses or other malicious software that may infect your Device, computer equipment, software, data or other property caused by you accessing, using or downloading from the Products, or from transmissions via emails or attachments received from us.\n" +
                "(iii) Any use of websites linked to the Products but operated by third parties.\n" +
                "(b) To the extent permitted by law, FELICITY and its affiliates, suppliers, clients, or licensors (collectively, the “Protected Entities”) shall not be liable for any consequential, exemplary or punitive damages arising from, or directly or indirectly related to, the use of, or the inability to use, the Products or the content, materials and functions related thereto, your provision of information via the Products, or lost business or lost sales, or any errors, viruses or bugs contained in the Products, even if such Protected Entity has been advised of the possibility of such damages. In no event shall the Protected Entities be liable for or in connection with any content posted, transmitted, exchanged or received by or on behalf of any user or other person on or through the Products. In no event shall the total aggregate liability of the Protected Entities to you for all damages, losses, and causes of action (whether in contract or tort, including, but not limited to, negligence or otherwise) arising from these terms of use or your use of the Products exceed, in the aggregate, the amount, if any, paid by you to FELICITY for your use of the Products.\n" +
                "\n" +
                "\n" +
                "13.12 ARBITRATION\n" +
                "PLEASE READ THE FOLLOWING PARAGRAPHS CAREFULLY, AS THEY REQUIRE YOU TO ARBITRATE DISPUTES WITH FELICITY, AND LIMIT THE MANNER IN WHICH YOU CAN SEEK RELIEF FROM FELICITY.\n" +
                "\n" +
                "(a) Applicability of Arbitration Agreement. All disputes arising out of, relating to, or in connection with these Terms or your use of the Products that cannot be resolved informally or in small claims court will be resolved through binding arbitration on an individual basis, except that you and FELICITY are not required to arbitrate any dispute in which either party seeks equitable relief for the alleged unlawful use of copyrights, trademarks, trade names, logos, trade secrets, or patents.\n" +
                "\n" +
                "(b) Arbitration Rules. The Federal Arbitration Act governs the interpretation and enforcement of this dispute-resolution provision. Arbitration will be initiated through the American Arbitration Association (\"AAA\"). If the AAA is not available to arbitrate, the parties will select an alternative arbitral forum. The rules of the arbitral forum will govern all aspects of this arbitration, except to the extent those rules conflict with these Terms. The AAA Consumer Arbitration Rules (“AAA Rules”) governing the arbitration are available online at www.adr.org or by calling the AAA at 1-800-778-7879. The arbitration will be conducted by a single neutral arbitrator.\n" +
                "\n" +
                "If the claim is for $10,000 or less, the party initiating the arbitration may choose whether the arbitration will be conducted (1) solely on the basis of documents submitted to the arbitrator; (2) through a non-appearance based telephonic hearing; or (3) by an in-person hearing as established by the AAA Rules in the county of your billing address. In the case of an in-person hearing, the proceedings will be conducted at a location which is reasonably convenient for both parties with due consideration of the ability to travel and other pertinent circumstances. If the parties are unable to agree on a location, the determination will be made by the arbitration institution.\n" +
                "\n" +
                "Your arbitration fees and your share of arbitrator compensation will be limited to those fees set forth in the AAA Rules with the remainder paid by FELICITY. If the arbitrator finds that either the substance of your claim or the relief sought in the arbitration is frivolous or brought for an improper purpose (as measured by the standards set forth in Federal Rule of Civil Procedure 11(b)), then the payment of all fees will be governed by the AAA Rules. In such case, you agree to reimburse FELICITY for all monies previously disbursed by it that are otherwise your obligation to pay under the AAA Rules. Regardless of the manner in which the arbitration is conducted, the arbitrator shall issue a reasoned written decision sufficient to explain the essential findings and conclusions on which the decision and award, if any, are based. The arbitrator may make rulings and resolve disputes as to the payment and reimbursement of fees or expenses at any time during the proceeding and upon request from either party made within 14 days of the arbitrator’s ruling on the merits.\n" +
                "\n" +
                "(c) Authority of Arbitrator. The arbitrator will decide the jurisdiction of the arbitrator and the rights and liabilities, if any, of you and FELICITY. The dispute will not be consolidated with any other matters or joined with any other cases or parties. The arbitrator will have the authority to grant motions dispositive of all or part of any claim or dispute. The arbitrator will have the authority to award all remedies available under applicable law, the arbitral forum\\'s rules, and the Terms. The arbitrator has the same authority to award relief on an individual basis that a judge in a court of law would have. The award of the arbitrator is final and binding upon you and FELICITY.\n" +
                "\n" +
                "(d) Jury Trial Waiver. You and FELICITY waive any constitutional and statutory rights to go to court and have a trial in front of a judge or a jury. Rather, you and FELICITY elect to have claims and disputes resolved by arbitration. In any litigation between you and FELICITY over whether to vacate or enforce an arbitration award, you and FELICITY waive all rights to a jury trial, and elect instead to have the dispute be resolved by a judge.\n" +
                "\n" +
                "(e) Class Action Waiver. WHERE PERMITTED UNDER THE APPLICABLE LAW, YOU AND FELICITY AGREE THAT EACH MAY BRING CLAIMS AGAINST THE OTHER ONLY IN YOUR OR OUR INDIVIDUAL CAPACITY AND NOT AS A PLAINTIFF OR CLASS MEMBER IN ANY PURPORTED CLASS OR CONSOLIDATED ACTION. If, however, this waiver of class or consolidated actions is deemed invalid or unenforceable, neither you nor FELICITY are entitled to arbitration; instead all claims and disputes will be resolved in a court as set forth in section 13.13 below.\n" +
                "\n" +
                "(f) Opt-out. YOU MAY OPT-OUT OF THIS ARBITRATION AGREEMENT. If you do so, neither you nor FELICITY can force the other to arbitrate. To opt-out, you must notify FELICITY in writing no later than 30 days after first becoming subject to this arbitration agreement. Your notice must include your name and address, and the email address you used to set up your FELICITY account (if you have one), and an unequivocal statement that you want to opt-out of this arbitration agreement. You must send your opt-out notice to one of the following physical or email addresses: FELICITY, Inc., ATTN: Arbitration Opt-out, 2415 Michigan Avenue, Santa Monica, CA 90404; ADR@FELICITY.com\n" +
                "\n" +
                "(g) Small Claims Court. Notwithstanding the foregoing, either you or FELICITY may bring an individual action in small claims court.\n" +
                "\n" +
                "(h) Arbitration Agreement Survival. This arbitration agreement will survive the termination of your relationship with FELICITY.\n" +
                "\n" +
                "13.13 EXCLUSIVE VENUE\n" +
                "To the extent the parties are permitted under these Terms to initiate litigation in a court, both you and FELICITY agree that all claims and disputes arising out of or relating to the Terms or the use of the Products will be litigated exclusively in the United States District Court for the Central District of California. If, however, that court would lack original jurisdiction over the litigation, then all claims and disputes arising out of or relating to the Terms or the use of the Products will be litigated exclusively in the Superior Court of California, County of Los Angeles. You and FELICITY consent to the personal jurisdiction of both courts.\n" +
                "\n" +
                "13.14 CHOICE OF LAW\n" +
                "Except to the extent they are preempted by U.S. federal law, the laws of California, other than its conflict-of-laws principles, govern these Terms and any disputes arising out of or relating to these Terms or their subject matter, including tort claims.\n" +
                "\n" +
                "13.15 SEVERABILITY\n" +
                "If any provision of these Terms is found unenforceable, then that provision will be severed from these Terms and not affect the validity and enforceability of any remaining provisions.\n" +
                "\n" +
                "These Terms are effective and were last updated on June 21, 2018.\n" +
                "\n" +
                "FELICITY, Inc. is located at 2415 Michigan Avenue, Santa Monica, CA 90404.\n");
        alertDialogBuilder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(Login.this, Demographics.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Decline",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                com.facebook.login.LoginManager.getInstance().logOut();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
