package com.utsdevelopers.vms.notification.domain;

import org.springframework.stereotype.Component;

@Component
public class EmailBuilder {


    public String buildCheckInEmail(String firstname, String tag, String purpose) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>VMS — Visitor Pass</title>
                </head>
                <body style="margin:0; padding:40px 16px; background:#0a1628; font-family:Arial, sans-serif;">

                <table width="100%%" cellpadding="0" cellspacing="0" border="0"
                       style="max-width:600px; margin:0 auto; background:#0d1f3c; border-radius:8px; overflow:hidden; border:1px solid #1e3a5f;">

                  <!-- HEADER -->
                  <tr>
                    <td style="background:linear-gradient(135deg, #0a1628 0%%, #0d2456 50%%, #1a3a7c 100%%); padding:36px 40px; text-align:center; border-bottom:2px solid #2563eb;">
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 auto 16px auto;">
                        <tr>
                          <td style="background:#1e3a5f; border-radius:8px; padding:10px 16px; text-align:center;">
                            <span style="font-family:Arial, sans-serif; font-size:13px; font-weight:700; color:#60a5fa; letter-spacing:2px;">
                              VMS
                            </span>
                          </td>
                        </tr>
                      </table>
                      <span style="font-family:Arial, sans-serif; font-size:22px; font-weight:700; letter-spacing:3px; color:#ffffff; display:block;">
                        UTS Developers
                      </span>
                      <span style="font-family:Arial, sans-serif; font-size:11px; letter-spacing:2px; color:#60a5fa; text-transform:uppercase; display:block; margin-top:6px;">
                        Visitor Management System
                      </span>
                    </td>
                  </tr>

                  <!-- BLUE ACCENT BAR -->
                  <tr>
                    <td style="background:linear-gradient(90deg, #1d4ed8, #2563eb, #3b82f6); padding:4px 0;"></td>
                  </tr>

                  <!-- BODY -->
                  <tr>
                    <td style="padding:44px 40px 32px 40px; background:#0d1f3c;">

                      <!-- Greeting -->
                      <p style="margin:0 0 20px 0; font-size:20px; font-weight:700; color:#f1f5f9; font-family:Arial, sans-serif;">
                        Hello, %s,
                      </p>

                      <!-- Body text -->
                      <p style="margin:0 0 32px 0; font-size:15px; line-height:1.8; color:#94a3b8;">
                        Your visit has been <strong style="color:#60a5fa;">successfully registered</strong>
                        in the Visitor Management System. Please present the tag number below
                        at the front desk upon arrival to gain access to the premises.
                      </p>

                      <!-- Divider -->
                      <div style="height:1px; background:#1e3a5f; margin:0 0 28px 0;"></div>

                      <!-- Tag number label -->
                      <p style="margin:0 0 8px 0; font-size:11px; letter-spacing:2px; color:#475569; text-transform:uppercase;">
                        Your Visitor Tag Number
                      </p>

                      <!-- Tag number box -->
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 0 32px 0; width:100%%;">
                        <tr>
                          <td style="background:linear-gradient(135deg, #1d4ed8 0%%, #2563eb 50%%, #3b82f6 100%%); border-radius:6px; padding:24px 36px; text-align:center;">
                            <span style="font-family:Arial, sans-serif; font-size:42px; font-weight:700; letter-spacing:10px; color:#ffffff; display:block;">
                              %s
                            </span>
                          </td>
                        </tr>
                      </table>

                      <!-- Visit details label -->
                      <p style="margin:0 0 16px 0; font-size:11px; letter-spacing:2px; color:#475569; text-transform:uppercase;">
                        Visit Details
                      </p>

                      <!-- Purpose -->
                      <p style="margin:0 0 8px 0; font-size:11px; letter-spacing:1px; color:#475569; text-transform:uppercase;">
                        Purpose of Visit
                      </p>
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 0 32px 0; width:100%%;">
                        <tr>
                          <td style="background:#0a1628; border-left:3px solid #60a5fa; border-radius:2px; padding:14px 20px;">
                            <span style="font-family:Arial, sans-serif; font-size:14px; color:#94a3b8;">
                              %s
                            </span>
                          </td>
                        </tr>
                      </table>

                      <!-- Closing -->
                      <p style="margin:0; font-size:14px; line-height:1.7; color:#64748b;">
                        If you did not schedule this visit or believe this was sent in error,
                        please contact the front desk immediately.
                      </p>

                    </td>
                  </tr>

                  <!-- DIVIDER -->
                  <tr>
                    <td style="padding:0 40px; background:#0d1f3c;">
                      <div style="height:1px; background:#1e3a5f;"></div>
                    </td>
                  </tr>

                  <!-- FOOTER -->
                  <tr>
                    <td style="background:#0a1628; padding:24px 40px; text-align:center;">
                      <p style="margin:0 0 6px 0; font-size:12px; color:#334155;">
                        &copy; 2026 <strong style="color:#2563eb;">UTS Developers</strong> — Visitor Management System. All rights reserved.
                      </p>
                      <p style="margin:0; font-size:11px; color:#1e3a5f; font-style:italic;">
                        This is an automated email. Please do not reply to this message.
                      </p>
                    </td>
                  </tr>

                </table>

                </body>
                </html>
                """.formatted(firstname, tag, purpose);
    }

    public String buildNotifyHostEmail(String firstname, String phoneNumber, String purpose) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>VMS — Visitor Notification</title>
                </head>
                <body style="margin:0; padding:40px 16px; background:#0a1628; font-family:Arial, sans-serif;">

                <table width="100%%" cellpadding="0" cellspacing="0" border="0"
                       style="max-width:600px; margin:0 auto; background:#0d1f3c; border-radius:8px; overflow:hidden; border:1px solid #1e3a5f;">

                  <!-- HEADER -->
                  <tr>
                    <td style="background:linear-gradient(135deg, #0a1628 0%%, #0d2456 50%%, #1a3a7c 100%%); padding:36px 40px; text-align:center; border-bottom:2px solid #2563eb;">
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 auto 16px auto;">
                        <tr>
                          <td style="background:#1e3a5f; border-radius:8px; padding:10px 16px; text-align:center;">
                            <span style="font-family:Arial, sans-serif; font-size:13px; font-weight:700; color:#60a5fa; letter-spacing:2px;">
                              VMS
                            </span>
                          </td>
                        </tr>
                      </table>
                      <span style="font-family:Arial, sans-serif; font-size:22px; font-weight:700; letter-spacing:3px; color:#ffffff; display:block;">
                        UTS Developers
                      </span>
                      <span style="font-family:Arial, sans-serif; font-size:11px; letter-spacing:2px; color:#60a5fa; text-transform:uppercase; display:block; margin-top:6px;">
                        Visitor Management System
                      </span>
                    </td>
                  </tr>

                  <!-- BLUE ACCENT BAR -->
                  <tr>
                    <td style="background:linear-gradient(90deg, #1d4ed8, #2563eb, #3b82f6); padding:4px 0;"></td>
                  </tr>

                  <!-- BODY -->
                  <tr>
                    <td style="padding:44px 40px 32px 40px; background:#0d1f3c;">

                      <!-- Greeting -->
                      <p style="margin:0 0 20px 0; font-size:20px; font-weight:700; color:#f1f5f9; font-family:Arial, sans-serif;">
                        Hello,
                      </p>

                      <!-- Body text -->
                      <p style="margin:0 0 32px 0; font-size:15px; line-height:1.8; color:#94a3b8;">
                        You have an <strong style="color:#60a5fa;">incoming visitor</strong> registered
                        in the Visitor Management System. The details of your visitor are outlined below.
                        Please make the necessary arrangements to receive them.
                      </p>

                      <!-- Divider -->
                      <div style="height:1px; background:#1e3a5f; margin:0 0 28px 0;"></div>

                      <!-- Visitor details label -->
                      <p style="margin:0 0 16px 0; font-size:11px; letter-spacing:2px; color:#475569; text-transform:uppercase;">
                        Visitor Details
                      </p>

                      <!-- Visitor name -->
                      <p style="margin:0 0 8px 0; font-size:11px; letter-spacing:1px; color:#475569; text-transform:uppercase;">
                        Visitor Name
                      </p>
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 0 16px 0; width:100%%;">
                        <tr>
                          <td style="background:#0a1628; border-left:3px solid #2563eb; border-radius:2px; padding:14px 20px;">
                            <span style="font-family:Arial, sans-serif; font-size:15px; font-weight:700; color:#f1f5f9;">
                              %s
                            </span>
                          </td>
                        </tr>
                      </table>

                      <!-- Phone number -->
                      <p style="margin:0 0 8px 0; font-size:11px; letter-spacing:1px; color:#475569; text-transform:uppercase;">
                        Phone Number
                      </p>
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 0 16px 0; width:100%%;">
                        <tr>
                          <td style="background:#0a1628; border-left:3px solid #2563eb; border-radius:2px; padding:14px 20px;">
                            <span style="font-family:Arial, sans-serif; font-size:14px; color:#94a3b8;">
                              %s
                            </span>
                          </td>
                        </tr>
                      </table>

                      <!-- Purpose -->
                      <p style="margin:0 0 8px 0; font-size:11px; letter-spacing:1px; color:#475569; text-transform:uppercase;">
                        Purpose of Visit
                      </p>
                      <table cellpadding="0" cellspacing="0" border="0" style="margin:0 0 32px 0; width:100%%;">
                        <tr>
                          <td style="background:#0a1628; border-left:3px solid #60a5fa; border-radius:2px; padding:14px 20px;">
                            <span style="font-family:Arial, sans-serif; font-size:14px; color:#94a3b8;">
                              %s
                            </span>
                          </td>
                        </tr>
                      </table>

                      <!-- Closing -->
                      <p style="margin:0; font-size:14px; line-height:1.7; color:#64748b;">
                        If you were not expecting this visitor or have any concerns,
                        please contact the front desk immediately.
                      </p>

                    </td>
                  </tr>

                  <!-- DIVIDER -->
                  <tr>
                    <td style="padding:0 40px; background:#0d1f3c;">
                      <div style="height:1px; background:#1e3a5f;"></div>
                    </td>
                  </tr>

                  <!-- FOOTER -->
                  <tr>
                    <td style="background:#0a1628; padding:24px 40px; text-align:center;">
                      <p style="margin:0 0 6px 0; font-size:12px; color:#334155;">
                        &copy; 2026 <strong style="color:#2563eb;">UTS Developers</strong> — Visitor Management System. All rights reserved.
                      </p>
                      <p style="margin:0; font-size:11px; color:#1e3a5f; font-style:italic;">
                        This is an automated email. Please do not reply to this message.
                      </p>
                    </td>
                  </tr>

                </table>

                </body>
                </html>
                """.formatted(firstname, phoneNumber, purpose);
    }
}