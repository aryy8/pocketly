import sys
from reportlab.lib.pagesizes import letter
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, PageBreak, KeepTogether, Table, TableStyle
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.enums import TA_CENTER, TA_LEFT, TA_JUSTIFY
from reportlab.lib import colors

def create_pdf(filename):
    # Setup document with standard margins (0.75 in / 54 pt)
    doc = SimpleDocTemplate(
        filename,
        pagesize=letter,
        rightMargin=54,
        leftMargin=54,
        topMargin=54,
        bottomMargin=54
    )

    styles = getSampleStyleSheet()

    # Custom styles matching original document
    style_university_header = ParagraphStyle(
        'UnivHeader',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=15,
        leading=20,
        alignment=TA_CENTER,
        textColor=colors.black
    )

    style_sub_header = ParagraphStyle(
        'SubHeader',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=12,
        leading=16,
        alignment=TA_CENTER,
        textColor=colors.black
    )

    style_synopsis_title = ParagraphStyle(
        'SynopsisTitle',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=18,
        leading=24,
        alignment=TA_CENTER,
        textColor=colors.black
    )

    style_synopsis_subtitle = ParagraphStyle(
        'SynopsisSubtitle',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=13,
        leading=18,
        alignment=TA_CENTER,
        textColor=colors.black
    )

    style_section_heading = ParagraphStyle(
        'SectionHeading',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=12,
        leading=16,
        alignment=TA_LEFT,
        textColor=colors.black,
        spaceAfter=4
    )

    style_body = ParagraphStyle(
        'BodyTextCustom',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=15,
        alignment=TA_JUSTIFY,
        textColor=colors.black
    )

    style_body_left = ParagraphStyle(
        'BodyTextLeft',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=15,
        alignment=TA_LEFT,
        textColor=colors.black
    )

    style_bullet = ParagraphStyle(
        'BulletCustom',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=15,
        alignment=TA_LEFT,
        leftIndent=15,
        textColor=colors.black
    )

    style_label = ParagraphStyle(
        'LabelStyle',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=16,
        alignment=TA_LEFT,
        textColor=colors.black
    )

    story = []

    # ================= PAGE 1 =================
    story.append(Spacer(1, 20))
    story.append(Paragraph("MANIPAL UNIVERSITY JAIPUR", style_university_header))
    story.append(Spacer(1, 4))
    story.append(Paragraph("School of Computer Science &amp; Engineering", style_sub_header))
    story.append(Spacer(1, 2))
    story.append(Paragraph("Department of Computer Science &amp; Engineering", style_sub_header))
    story.append(Spacer(1, 45))

    story.append(Paragraph("PROJECT SYNOPSIS", style_synopsis_title))
    story.append(Spacer(1, 6))
    story.append(Paragraph("for Android App Development", style_synopsis_subtitle))
    story.append(Spacer(1, 55))

    story.append(Paragraph("Project Title", style_sub_header))
    story.append(Spacer(1, 8))
    title_p = Paragraph("<u>“Pocketly: Personal Finance &amp; Expense Tracker”</u>", ParagraphStyle(
        'TitleFilled', parent=style_synopsis_subtitle, fontName='Times-Bold', fontSize=13, leading=18
    ))
    story.append(title_p)
    story.append(Spacer(1, 65))

    # Details table for Page 1
    p1_details = [
        [Paragraph("Student Name", style_label), Paragraph("<u>Aryan Rastogi</u>", ParagraphStyle('Val1', parent=style_label, fontName='Times-Bold'))],
        [Paragraph("Registration No.", style_label), Paragraph("<u>2427030083</u>", ParagraphStyle('Val2', parent=style_label, fontName='Times-Bold'))],
        [Paragraph("Course / Lab", style_label), Paragraph("Android App Development Laboratory", style_label)],
        [Paragraph("University", style_label), Paragraph("Manipal University Jaipur", style_label)],
    ]
    t1 = Table(p1_details, colWidths=[130, 350])
    t1.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('BOTTOMPADDING', (0,0), (-1,-1), 8),
        ('TOPPADDING', (0,0), (-1,-1), 8),
    ]))
    story.append(t1)

    story.append(PageBreak())

    # ================= PAGE 2 =================
    story.append(Spacer(1, 10))
    story.append(Paragraph("SYNOPSIS", ParagraphStyle('SynHeader', parent=style_university_header, alignment=TA_LEFT, fontSize=13, leading=17)))
    story.append(Spacer(1, 12))

    story.append(Paragraph("1. Project Title", style_section_heading))
    story.append(Paragraph("<u>Pocketly: Personal Finance &amp; Expense Tracker</u>", ParagraphStyle('P2Title', parent=style_body_left, fontName='Times-Bold')))
    story.append(Spacer(1, 14))

    story.append(Paragraph("2. Abstract", style_section_heading))
    abstract_text = (
        "This project proposes the design and development of an Android-based mobile application for "
        "managing <u>personal finances, daily transaction histories, connected bank accounts, and spending analytics</u>. "
        "The application will demonstrate the practical application of Android app development concepts, "
        "including user interface design, activity and screen navigation, event handling, input validation, "
        "data storage, and application lifecycle management. The project will be developed using Android Studio "
        "with Kotlin and XML where appropriate. The proposed application is intended to provide an organized, "
        "user-friendly, and efficient solution for performing common tasks related to the selected problem domain. "
        "The project will emphasize responsive user interaction, proper data handling, usability, and reliable "
        "application behavior. Through this implementation, students will gain hands-on experience in converting a "
        "real-world problem into a functional Android mobile application."
    )
    story.append(Paragraph(abstract_text, style_body))
    story.append(Spacer(1, 14))

    story.append(Paragraph("3. Problem Statement", style_section_heading))
    problem_text = (
        "The objective of this project is to identify a real-world problem that can be addressed through a mobile "
        "application and develop an Android-based solution that efficiently allows users to enter, process, view, "
        "update, and manage the required information. The application should provide an intuitive user interface, "
        "validate user input, handle user interactions correctly, and maintain data consistently throughout the application."
    )
    story.append(Paragraph(problem_text, style_body))
    story.append(Spacer(1, 14))

    story.append(Paragraph("4. Objectives", style_section_heading))
    objectives = [
        "· To analyze the selected real-world problem and identify its functional requirements.",
        "· To design an appropriate and user-friendly Android application interface.",
        "· To implement application screens using XML layouts and Kotlin.",
        "· To handle user interactions using buttons, input fields, RadioButtons, CheckBoxes, and other UI components as required.",
        "· To implement navigation between activities/screens.",
        "· To validate user input and provide appropriate feedback to the user.",
        "· To implement suitable local data storage and retrieval where required.",
        "· To demonstrate Android application lifecycle and event-handling concepts.",
        "· To test the application for functionality, usability, and basic error handling."
    ]
    for obj in objectives:
        story.append(Paragraph(obj, style_bullet))
        story.append(Spacer(1, 3))

    story.append(PageBreak())

    # ================= PAGE 3 =================
    story.append(Spacer(1, 10))
    story.append(Paragraph("5. Proposed Application Components", style_section_heading))
    components_text = (
        "The project may contain multiple application screens and components such as "
        "<u>Dashboard Feed (HomeScreen with Balance &amp; Spending cards)</u>, "
        "<u>Transaction Management (TransactionsScreen with Search &amp; Filters)</u>, and "
        "<u>Analytics &amp; Profile (AnalyticsScreen &amp; ProfileScreen)</u>. "
        "The exact screens, features, UI components, and data requirements will be finalized after analysis of the selected problem domain."
    )
    story.append(Paragraph(components_text, style_body))
    story.append(Spacer(1, 14))

    story.append(Paragraph("6. Android Development Concepts to be Demonstrated", style_section_heading))
    concepts = [
        "· Android Studio project structure and configuration",
        "· Kotlin programming for Android application logic",
        "· XML layouts and user interface components",
        "· Activities and activity lifecycle",
        "· Intent-based navigation between screens",
        "· Event handling for buttons and other UI controls",
        "· EditText, TextView, Button, ImageView, RadioButton, CheckBox, Spinner, and other suitable components",
        "· Input validation and user feedback using Toasts or dialogs",
        "· RecyclerView and Adapter where applicable",
        "· Local data storage using SQLite/Room/SharedPreferences, where applicable",
        "· Basic exception handling and application testing"
    ]
    for conc in concepts:
        story.append(Paragraph(conc, style_bullet))
        story.append(Spacer(1, 3))
    story.append(Spacer(1, 14))

    story.append(Paragraph("7. Expected Outcome", style_section_heading))
    outcome_text = (
        "The expected outcome is a functional Android mobile application prototype capable of addressing the "
        "selected problem domain. The project will demonstrate appropriate Android UI design, Kotlin-based "
        "application logic, event handling, screen navigation, input validation, data management where required, "
        "and practical application of Android app development concepts."
    )
    story.append(Paragraph(outcome_text, style_body))
    story.append(Spacer(1, 16))

    story.append(Paragraph("8. Student Details", style_section_heading))
    story.append(Spacer(1, 6))

    p3_details = [
        [Paragraph("Student Name", style_label), Paragraph("<u>Aryan Rastogi</u>", ParagraphStyle('Val3', parent=style_label, fontName='Times-Bold'))],
        [Paragraph("Registration No.", style_label), Paragraph("<u>2427030083</u>", ParagraphStyle('Val4', parent=style_label, fontName='Times-Bold'))],
        [Paragraph("Section / Semester", style_label), Paragraph("<u>Section I / 5th Semester</u>", ParagraphStyle('Val5', parent=style_label, fontName='Times-Bold'))],
        [Paragraph("Student Signature:", style_label), Paragraph("<u>Aryan Rastogi</u>", ParagraphStyle('Val6', parent=style_label, fontName='Times-Bold'))],
    ]
    t3 = Table(p3_details, colWidths=[130, 350])
    t3.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('BOTTOMPADDING', (0,0), (-1,-1), 8),
        ('TOPPADDING', (0,0), (-1,-1), 8),
    ]))
    story.append(t3)

    doc.build(story)
    print("PDF generated successfully:", filename)

if __name__ == '__main__':
    output_filename = "Android_App_Development_Project_Synopsis_Aryan_Rastogi.pdf"
    if len(sys.argv) > 1:
        output_filename = sys.argv[1]
    create_pdf(output_filename)
